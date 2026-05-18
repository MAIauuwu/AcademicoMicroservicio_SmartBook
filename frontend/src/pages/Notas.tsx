import { useState, useEffect } from 'react';
import { notaService, evaluacionService } from '../services/api';
import type { Nota, Evaluacion } from '../types';

const emptyNota: Nota = { estudianteId: 0, valor: 0, evaluacionId: 0 };

export default function Notas() {
  const [notas, setNotas] = useState<Nota[]>([]);
  const [form, setForm] = useState<Nota>(emptyNota);
  const [editing, setEditing] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [evaluaciones, setEvaluaciones] = useState<Evaluacion[]>([]);

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const [notasRes, evalRes] = await Promise.all([
        notaService.getAll(),
        evaluacionService.getAll(),
      ]);
      setNotas(notasRes.data);
      setEvaluaciones(evalRes.data);
    } catch (error) {
      console.error('Error al cargar datos:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (editing) {
        await notaService.update(editing, form);
      } else {
        await notaService.create(form);
      }
      setForm(emptyNota);
      setEditing(null);
      setShowModal(false);
      loadData();
    } catch (error) {
      console.error('Error al guardar:', error);
    }
  };

  const handleEdit = (nota: Nota) => {
    setForm(nota);
    setEditing(nota.id!);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('¿Estás seguro de eliminar esta nota?')) {
      try {
        await notaService.delete(id);
        loadData();
      } catch (error) {
        console.error('Error al eliminar:', error);
      }
    }
  };

  const handleNew = () => {
    setForm(emptyNota);
    setEditing(null);
    setShowModal(true);
  };

  if (loading) return <div className="text-center py-8">Cargando...</div>;

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Notas</h1>
        <button onClick={handleNew} className="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition-colors">
          + Nueva Nota
        </button>
      </div>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Estudiante ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Evaluación</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Valor</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Acciones</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {notas.map((nota) => (
              <tr key={nota.id} className="hover:bg-gray-50">
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{nota.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{nota.estudianteId}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{nota.evaluacionNombre}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{nota.valor}</td>
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button onClick={() => handleEdit(nota)} className="text-indigo-600 hover:text-indigo-900 mr-4">Editar</button>
                  <button onClick={() => handleDelete(nota.id!)} className="text-red-600 hover:text-red-900">Eliminar</button>
                </td>
              </tr>
            ))}
            {notas.length === 0 && (
              <tr><td colSpan={5} className="px-6 py-4 text-center text-gray-500">No hay notas registradas</td></tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-md">
            <h2 className="text-xl font-bold mb-4">{editing ? 'Editar' : 'Nueva'} Nota</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Estudiante ID</label>
                <input type="number" required value={form.estudianteId} onChange={(e) => setForm({ ...form, estudianteId: parseInt(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Evaluación</label>
                <select required value={form.evaluacionId} onChange={(e) => setForm({ ...form, evaluacionId: parseInt(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500">
                  <option value={0}>Seleccionar evaluación</option>
                  {evaluaciones.map((ev) => <option key={ev.id} value={ev.id}>{ev.nombre}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Valor</label>
                <input type="number" required min={0} step={0.1} value={form.valor} onChange={(e) => setForm({ ...form, valor: parseFloat(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500" />
              </div>
              <div className="flex justify-end space-x-3 pt-4">
                <button type="button" onClick={() => setShowModal(false)} className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50">Cancelar</button>
                <button type="submit" className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700">{editing ? 'Actualizar' : 'Crear'}</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

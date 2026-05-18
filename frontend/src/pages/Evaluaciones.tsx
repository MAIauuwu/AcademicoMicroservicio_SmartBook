import { useState, useEffect } from 'react';
import { evaluacionService, cursoAsignaturaService } from '../services/api';
import type { Evaluacion, CursoAsignatura } from '../types';

const emptyEvaluacion: Evaluacion = { nombre: '', descripcion: '', fecha: '', peso: 0, notaMaxima: 0, cursoAsignaturaId: 0 };

export default function Evaluaciones() {
  const [evaluaciones, setEvaluaciones] = useState<Evaluacion[]>([]);
  const [form, setForm] = useState<Evaluacion>(emptyEvaluacion);
  const [editing, setEditing] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [cursosAsignatura, setCursosAsignatura] = useState<CursoAsignatura[]>([]);

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      const [evalRes, caRes] = await Promise.all([
        evaluacionService.getAll(),
        cursoAsignaturaService.getAll(),
      ]);
      setEvaluaciones(evalRes.data);
      setCursosAsignatura(caRes.data);
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
        await evaluacionService.update(editing, form);
      } else {
        await evaluacionService.create(form);
      }
      setForm(emptyEvaluacion);
      setEditing(null);
      setShowModal(false);
      loadData();
    } catch (error) {
      console.error('Error al guardar:', error);
    }
  };

  const handleEdit = (evalItem: Evaluacion) => {
    setForm(evalItem);
    setEditing(evalItem.id!);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('¿Estás seguro de eliminar esta evaluación?')) {
      try {
        await evaluacionService.delete(id);
        loadData();
      } catch (error) {
        console.error('Error al eliminar:', error);
      }
    }
  };

  const handleNew = () => {
    setForm(emptyEvaluacion);
    setEditing(null);
    setShowModal(true);
  };

  if (loading) return <div className="text-center py-8">Cargando...</div>;

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Evaluaciones</h1>
        <button onClick={handleNew} className="bg-orange-600 text-white px-4 py-2 rounded-lg hover:bg-orange-700 transition-colors">
          + Nueva Evaluación
        </button>
      </div>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nombre</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fecha</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Peso</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nota Máx</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Acciones</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {evaluaciones.map((evalItem) => (
              <tr key={evalItem.id} className="hover:bg-gray-50">
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{evalItem.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{evalItem.nombre}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{evalItem.fecha}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{evalItem.peso}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{evalItem.notaMaxima}</td>
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button onClick={() => handleEdit(evalItem)} className="text-indigo-600 hover:text-indigo-900 mr-4">Editar</button>
                  <button onClick={() => handleDelete(evalItem.id!)} className="text-red-600 hover:text-red-900">Eliminar</button>
                </td>
              </tr>
            ))}
            {evaluaciones.length === 0 && (
              <tr><td colSpan={6} className="px-6 py-4 text-center text-gray-500">No hay evaluaciones registradas</td></tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-md">
            <h2 className="text-xl font-bold mb-4">{editing ? 'Editar' : 'Nueva'} Evaluación</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nombre</label>
                <input type="text" required maxLength={100} value={form.nombre} onChange={(e) => setForm({ ...form, nombre: e.target.value })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Descripción</label>
                <textarea maxLength={500} value={form.descripcion} onChange={(e) => setForm({ ...form, descripcion: e.target.value })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500" rows={2} />
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Fecha</label>
                  <input type="date" required value={form.fecha} onChange={(e) => setForm({ ...form, fecha: e.target.value })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500" />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Nota Máxima</label>
                  <input type="number" required min={1} step={0.1} value={form.notaMaxima} onChange={(e) => setForm({ ...form, notaMaxima: parseFloat(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500" />
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Peso (%)</label>
                <input type="number" required min={0} step={0.1} value={form.peso} onChange={(e) => setForm({ ...form, peso: parseFloat(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Curso-Asignatura</label>
                <select required value={form.cursoAsignaturaId} onChange={(e) => setForm({ ...form, cursoAsignaturaId: parseInt(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-orange-500">
                  <option value={0}>Seleccionar</option>
                  {cursosAsignatura.map((ca) => <option key={ca.id} value={ca.id}>{ca.cursoNombre} - {ca.asignaturaNombre}</option>)}
                </select>
              </div>
              <div className="flex justify-end space-x-3 pt-4">
                <button type="button" onClick={() => setShowModal(false)} className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50">Cancelar</button>
                <button type="submit" className="px-4 py-2 bg-orange-600 text-white rounded-md hover:bg-orange-700">{editing ? 'Actualizar' : 'Crear'}</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

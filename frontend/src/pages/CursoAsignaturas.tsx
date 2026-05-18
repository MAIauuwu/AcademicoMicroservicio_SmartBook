import { useState, useEffect } from 'react';
import { cursoAsignaturaService, cursoService, asignaturaService } from '../services/api';
import type { CursoAsignatura, Curso, Asignatura } from '../types';

const emptyCursoAsignatura: CursoAsignatura = { cursoId: 0, asignaturaId: 0, docenteId: 0, semestre: '' };

export default function CursoAsignaturas() {
  const [items, setItems] = useState<CursoAsignatura[]>([]);
  const [form, setForm] = useState<CursoAsignatura>(emptyCursoAsignatura);
  const [editing, setEditing] = useState<number | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [cursos, setCursos] = useState<Curso[]>([]);
  const [asignaturas, setAsignaturas] = useState<Asignatura[]>([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [itemsRes, cursosRes, asignaturasRes] = await Promise.all([
        cursoAsignaturaService.getAll(),
        cursoService.getAll(),
        asignaturaService.getAll(),
      ]);
      setItems(itemsRes.data);
      setCursos(cursosRes.data);
      setAsignaturas(asignaturasRes.data);
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
        await cursoAsignaturaService.update(editing, form);
      } else {
        await cursoAsignaturaService.create(form);
      }
      setForm(emptyCursoAsignatura);
      setEditing(null);
      setShowModal(false);
      loadData();
    } catch (error) {
      console.error('Error al guardar:', error);
    }
  };

  const handleEdit = (item: CursoAsignatura) => {
    setForm(item);
    setEditing(item.id!);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm('¿Estás seguro de eliminar esta relación?')) {
      try {
        await cursoAsignaturaService.delete(id);
        loadData();
      } catch (error) {
        console.error('Error al eliminar:', error);
      }
    }
  };

  const handleNew = () => {
    setForm(emptyCursoAsignatura);
    setEditing(null);
    setShowModal(true);
  };

  if (loading) return <div className="text-center py-8">Cargando...</div>;

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-800">Curso-Asignatura</h1>
        <button onClick={handleNew} className="bg-purple-600 text-white px-4 py-2 rounded-lg hover:bg-purple-700 transition-colors">
          + Nueva Relación
        </button>
      </div>

      <div className="bg-white rounded-lg shadow overflow-hidden">
        <table className="min-w-full divide-y divide-gray-200">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Curso</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Asignatura</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Docente ID</th>
              <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Semestre</th>
              <th className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Acciones</th>
            </tr>
          </thead>
          <tbody className="bg-white divide-y divide-gray-200">
            {items.map((item) => (
              <tr key={item.id} className="hover:bg-gray-50">
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{item.id}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">{item.cursoNombre}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{item.asignaturaNombre}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{item.docenteId}</td>
                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{item.semestre}</td>
                <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                  <button onClick={() => handleEdit(item)} className="text-indigo-600 hover:text-indigo-900 mr-4">Editar</button>
                  <button onClick={() => handleDelete(item.id!)} className="text-red-600 hover:text-red-900">Eliminar</button>
                </td>
              </tr>
            ))}
            {items.length === 0 && (
              <tr><td colSpan={6} className="px-6 py-4 text-center text-gray-500">No hay relaciones registradas</td></tr>
            )}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-md">
            <h2 className="text-xl font-bold mb-4">{editing ? 'Editar' : 'Nueva'} Relación</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Curso</label>
                <select required value={form.cursoId} onChange={(e) => setForm({ ...form, cursoId: parseInt(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500">
                  <option value={0}>Seleccionar curso</option>
                  {cursos.map((c) => <option key={c.id} value={c.id}>{c.nombre}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Asignatura</label>
                <select required value={form.asignaturaId} onChange={(e) => setForm({ ...form, asignaturaId: parseInt(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500">
                  <option value={0}>Seleccionar asignatura</option>
                  {asignaturas.map((a) => <option key={a.id} value={a.id}>{a.nombre}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Docente ID</label>
                <input type="number" required value={form.docenteId} onChange={(e) => setForm({ ...form, docenteId: parseInt(e.target.value) })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Semestre</label>
                <input type="text" required value={form.semestre} onChange={(e) => setForm({ ...form, semestre: e.target.value })} className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500" placeholder="Ej: 2026-1" />
              </div>
              <div className="flex justify-end space-x-3 pt-4">
                <button type="button" onClick={() => setShowModal(false)} className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50">Cancelar</button>
                <button type="submit" className="px-4 py-2 bg-purple-600 text-white rounded-md hover:bg-purple-700">{editing ? 'Actualizar' : 'Crear'}</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

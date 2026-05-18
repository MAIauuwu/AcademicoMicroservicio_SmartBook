import axios from 'axios';
import type { Curso, Asignatura, CursoAsignatura, Evaluacion, Nota } from '../types';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const cursoService = {
  getAll: () => api.get<Curso[]>('/cursos'),
  getById: (id: number) => api.get<Curso>(`/cursos/${id}`),
  create: (data: Curso) => api.post<Curso>('/cursos', data),
  update: (id: number, data: Curso) => api.put<Curso>(`/cursos/${id}`, data),
  delete: (id: number) => api.delete(`/cursos/${id}`),
};

export const asignaturaService = {
  getAll: () => api.get<Asignatura[]>('/asignaturas'),
  getById: (id: number) => api.get<Asignatura>(`/asignaturas/${id}`),
  create: (data: Asignatura) => api.post<Asignatura>('/asignaturas', data),
  update: (id: number, data: Asignatura) => api.put<Asignatura>(`/asignaturas/${id}`, data),
  delete: (id: number) => api.delete(`/asignaturas/${id}`),
};

export const cursoAsignaturaService = {
  getAll: () => api.get<CursoAsignatura[]>('/cursos-asignaturas'),
  getById: (id: number) => api.get<CursoAsignatura>(`/cursos-asignaturas/${id}`),
  create: (data: CursoAsignatura) => api.post<CursoAsignatura>('/cursos-asignaturas', data),
  update: (id: number, data: CursoAsignatura) => api.put<CursoAsignatura>(`/cursos-asignaturas/${id}`, data),
  delete: (id: number) => api.delete(`/cursos-asignaturas/${id}`),
};

export const evaluacionService = {
  getAll: () => api.get<Evaluacion[]>('/evaluaciones'),
  getById: (id: number) => api.get<Evaluacion>(`/evaluaciones/${id}`),
  create: (data: Evaluacion) => api.post<Evaluacion>('/evaluaciones', data),
  update: (id: number, data: Evaluacion) => api.put<Evaluacion>(`/evaluaciones/${id}`, data),
  delete: (id: number) => api.delete(`/evaluaciones/${id}`),
};

export const notaService = {
  getAll: () => api.get<Nota[]>('/notas'),
  getById: (id: number) => api.get<Nota>(`/notas/${id}`),
  create: (data: Nota) => api.post<Nota>('/notas', data),
  update: (id: number, data: Nota) => api.put<Nota>(`/notas/${id}`, data),
  delete: (id: number) => api.delete(`/notas/${id}`),
};

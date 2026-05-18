export interface Curso {
  id?: number;
  nombre: string;
  descripcion: string;
  anio: number;
  periodo: string;
}

export interface Asignatura {
  id?: number;
  nombre: string;
  descripcion: string;
  creditos: number;
}

export interface CursoAsignatura {
  id?: number;
  cursoId: number;
  cursoNombre?: string;
  asignaturaId: number;
  asignaturaNombre?: string;
  docenteId: number;
  semestre: string;
}

export interface Evaluacion {
  id?: number;
  nombre: string;
  descripcion: string;
  fecha: string;
  peso: number;
  notaMaxima: number;
  cursoAsignaturaId: number;
}

export interface Nota {
  id?: number;
  estudianteId: number;
  valor: number;
  evaluacionId: number;
  evaluacionNombre?: string;
}

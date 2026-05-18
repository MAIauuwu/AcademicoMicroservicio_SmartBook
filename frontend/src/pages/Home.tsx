import { Link } from 'react-router-dom';

export default function Home() {
  const sections = [
    { path: '/cursos', title: 'Cursos', desc: 'Gestiona los cursos académicos', icon: '📚', color: 'bg-blue-500' },
    { path: '/asignaturas', title: 'Asignaturas', desc: 'Administra las asignaturas', icon: '📖', color: 'bg-green-500' },
    { path: '/cursos-asignaturas', title: 'Curso-Asignatura', desc: 'Asigna asignaturas a cursos', icon: '🔗', color: 'bg-purple-500' },
    { path: '/evaluaciones', title: 'Evaluaciones', desc: 'Crea evaluaciones y exámenes', icon: '📝', color: 'bg-orange-500' },
    { path: '/notas', title: 'Notas', desc: 'Registra las calificaciones', icon: '📊', color: 'bg-red-500' },
  ];

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-800 mb-4">SmartBook Académico</h1>
        <p className="text-xl text-gray-600">Sistema de gestión académica para microservicios</p>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {sections.map((section) => (
          <Link
            key={section.path}
            to={section.path}
            className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow p-6 border border-gray-200"
          >
            <div className={`${section.color} w-12 h-12 rounded-full flex items-center justify-center text-2xl mb-4`}>
              {section.icon}
            </div>
            <h2 className="text-xl font-semibold text-gray-800 mb-2">{section.title}</h2>
            <p className="text-gray-600">{section.desc}</p>
          </Link>
        ))}
      </div>
    </div>
  );
}

# AcademicoMicroservicio_SmartBook

Sistema de gestión académico completo con backend en Spring Boot (Java) y frontend en React + TypeScript, conectado a PostgreSQL.

## Características

- Gestión de Estudiantes
- Gestión de Docentes
- Gestión de Cursos
- Gestión de Asignaturas
- Asignación de cursos con asignaturas y docentes
- Gestión de Evaluaciones
- Gestión de Notas

## Requisitos

- Java 17+
- Maven 3.8+
- Node.js 18+
- Docker y Docker Compose (opcional, para PostgreSQL)

## Estructura del Proyecto

```
AcademicoMicroservicio_SmartBook/
├── frontend/                 # Frontend React + TypeScript
│   ├── src/
│   │   ├── components/      # Componentes reutilizables
│   │   ├── pages/           # Páginas de la aplicación
│   │   ├── services/        # Servicios API
│   │   └── types/           # Definiciones TypeScript
│   └── package.json
├── src/                     # Backend Java Spring Boot
│   └── main/
│       └── java/org/bubbleplat/academico/
│           ├── controller/  # Controladores REST
│           ├── service/     # Lógica de negocio
│           ├── repository/  # Acceso a datos
│           ├── entity/      # Entidades JPA
│           ├── dto/         # Data Transfer Objects
│           └── config/      # Configuraciones
├── docker-compose.yml       # PostgreSQL
└── pom.xml                  # Maven
```

## Configuración de la Base de Datos

### Opción 1: Usar Docker (Recomendado)

```bash
docker-compose up -d
```

Esto iniciará PostgreSQL en el puerto 5432 con:
- Base de datos: `academico_db`
- Usuario: `postgres`
- Contraseña: `postgres`

### Opción 2: PostgreSQL Local

Si tienes PostgreSQL instalado localmente, crea la base de datos:

```sql
CREATE DATABASE academico_db;
```

## Ejecución del Backend

```bash
# Compilar y ejecutar
mvn spring-boot:run

# O compilar el JAR y ejecutar
mvn clean package
java -jar target/AcademicoMicrosercio_smartbook-1.0-SNAPSHOT.jar
```

El backend estará disponible en: http://localhost:8084

## Ejecución del Frontend

```bash
cd frontend

# Instalar dependencias (primera vez)
npm install

# Ejecutar en modo desarrollo
npm run dev

# O construir para producción
npm run build
npm run preview
```

El frontend estará disponible en: http://localhost:5173

## API Endpoints

### Estudiantes
- `GET /api/estudiantes` - Listar todos
- `GET /api/estudiantes/{id}` - Obtener por ID
- `GET /api/estudiantes/email/{email}` - Obtener por email
- `GET /api/estudiantes/matricula/{matricula}` - Obtener por matrícula
- `POST /api/estudiantes` - Crear
- `PUT /api/estudiantes/{id}` - Actualizar
- `DELETE /api/estudiantes/{id}` - Eliminar

### Docentes
- `GET /api/docentes` - Listar todos
- `GET /api/docentes/{id}` - Obtener por ID
- `GET /api/docentes/email/{email}` - Obtener por email
- `POST /api/docentes` - Crear
- `PUT /api/docentes/{id}` - Actualizar
- `DELETE /api/docentes/{id}` - Eliminar

### Cursos
- `GET /api/cursos` - Listar todos
- `GET /api/cursos/{id}` - Obtener por ID
- `GET /api/cursos/anio/{anio}` - Filtrar por año
- `GET /api/cursos/periodo/{periodo}` - Filtrar por periodo
- `POST /api/cursos` - Crear
- `PUT /api/cursos/{id}` - Actualizar
- `DELETE /api/cursos/{id}` - Eliminar

### Asignaturas
- `GET /api/asignaturas` - Listar todas
- `GET /api/asignaturas/{id}` - Obtener por ID
- `GET /api/asignaturas/nombre/{nombre}` - Obtener por nombre
- `POST /api/asignaturas` - Crear
- `PUT /api/asignaturas/{id}` - Actualizar
- `DELETE /api/asignaturas/{id}` - Eliminar

### Cursos-Asignaturas
- `GET /api/cursos-asignaturas` - Listar todos
- `GET /api/cursos-asignaturas/{id}` - Obtener por ID
- `GET /api/cursos-asignaturas/docente/{docenteId}` - Filtrar por docente
- `GET /api/cursos-asignaturas/curso/{cursoId}` - Filtrar por curso
- `GET /api/cursos-asignaturas/semestre/{semestre}` - Filtrar por semestre
- `POST /api/cursos-asignaturas` - Crear
- `PUT /api/cursos-asignaturas/{id}` - Actualizar
- `DELETE /api/cursos-asignaturas/{id}` - Eliminar

### Evaluaciones
- `GET /api/evaluaciones` - Listar todas
- `GET /api/evaluaciones/{id}` - Obtener por ID
- `GET /api/evaluaciones/curso-asignatura/{cursoAsignaturaId}` - Filtrar por curso-asignatura
- `POST /api/evaluaciones` - Crear
- `PUT /api/evaluaciones/{id}` - Actualizar
- `DELETE /api/evaluaciones/{id}` - Eliminar

### Notas
- `GET /api/notas` - Listar todas
- `GET /api/notas/{id}` - Obtener por ID
- `GET /api/notas/estudiante/{estudianteId}` - Filtrar por estudiante
- `GET /api/notas/evaluacion/{evaluacionId}` - Filtrar por evaluación
- `POST /api/notas` - Crear
- `PUT /api/notas/{id}` - Actualizar
- `DELETE /api/notas/{id}` - Eliminar

## Tecnologías

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Lombok
- Jakarta Validation

### Frontend
- React 18
- TypeScript
- Vite
- Tailwind CSS
- React Router DOM
- React Hook Form
- Zod (validación)
- Lucide React (iconos)

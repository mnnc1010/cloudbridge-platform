# CloudBridge Platform

A full-stack cloud-native platform featuring a Spring Boot backend, Angular frontend, Docker-based development, Kubernetes deployments, and multi-cloud infrastructure support.

## Project Structure

```text
cloudbridge-platform/
│
├── backend/                           # 🌱 Spring Boot backend service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/mnnc1010/cloudbridge/   # Java source code
│   │   │   │   ├── controller/       # REST controllers
│   │   │   │   ├── service/          # Business logic
│   │   │   │   ├── model/            # Data models / entities
│   │   │   │   └── CloudbridgeApplication.java  # Main app
│   │   │   └── resources/
│   │   │       ├── application.yaml  # Main Spring Boot config
│   │   │       ├── logback.xml       # Logging configuration
│   │   │       └── static/           # Static content (if needed)
│   │   └── test/
│   │       └── java/...              # Unit and integration tests
│   ├── pom.xml                       # Maven project descriptor
│   └── README.md                     # Optional backend-specific docs
│
├── frontend/                         # 💻 Angular frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/           # Angular components (UI)
│   │   │   ├── services/             # API services and interceptors
│   │   │   └── app.module.ts         # Main Angular module
│   │   ├── assets/                   # Images, icons, styles
│   │   └── environments/             # Angular envs (dev, prod)
│   ├── angular.json                  # Angular CLI config
│   ├── package.json                  # npm package definitions
│   ├── tsconfig.json                 # TypeScript configuration
│   └── README.md                     # Optional frontend-specific docs
│
├── k8s/                              # ☸️ Kubernetes deployment files
│   ├── backend-deployment.yaml       # Backend Deployment & Service
│   ├── frontend-deployment.yaml      # Frontend Deployment & Service
│   ├── mongo-deployment.yaml         # MongoDB StatefulSet/Deployment
│   └── ingress.yaml                  # Ingress resource (if needed)
│
├── environments/                     # ⚙️ Configurations per environment
│   ├── local/
│   │   └── application.yaml
│   ├── dev/
│   │   └── application.yaml
│   ├── staging/
│   │   └── application.yaml
│   └── prod/
│       └── application.yaml
├── infra/
│   ├── aws/
│   ├── gcp/
│   └── azure/
├── docker-compose.yml                # 🐳 Local development runner (backend, frontend, MongoDB)
├── .gitignore                        # 🔒 Ignore files (IDE, build artifacts, etc.)
└── README.md                         # 📘 Main project documentation
```

- **backend/**: Spring Boot backend service (Java 17, Maven)
- **frontend/**: Angular frontend application
- **k8s/**: Kubernetes deployment manifests
- **environments/**: Environment-specific configurations (local, dev, staging, prod)
- **infra/**: Cloud infrastructure scripts/configurations (AWS, GCP, Azure)
- **docker-compose.yml**: Local development runner for containers
- **.gitignore**: Files/folders to be ignored in version control

## Getting Started

Please refer to the individual README files in the `backend/` and `frontend/` directories for setup instructions.
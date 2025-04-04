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
├── frontend/
│   │── shell/                           # Container/shell app that brings all frontends together.
│   │   ├── src/
│   │   │   ├── app/
│   │   │   │   ├── components/
│   │   │   │   │   ├── angular-summary-wrapper/      # A wrapper component for the Angular app.
│   │   │   │   │   │   ├── angular-summary-wrapper.component.ts
│   │   │   │   │   │   ├── angular-summary-wrapper.component.html
│   │   │   │   │   │   └── angular-summary-wrapper.component.css
│   │   │   │   │   ├── react-insert-wrapper/         # A wrapper component for the React app.
│   │   │   │   │   │   ├── react-insert-wrapper.component.ts
│   │   │   │   │   │   ├── react-insert-wrapper.component.html
│   │   │   │   │   │   └── react-insert-wrapper.component.css
│   │   │   │   │   └── vue-stream-wrapper/           # A wrapper component for the Vue app.
│   │   │   │   │       ├── vue-stream-wrapper.component.ts
│   │   │   │   │       ├── vue-stream-wrapper.component.html
│   │   │   │   │       └── vue-stream-wrapper.component.css
│   │   │   │   └── app.component.ts                 # The main container component.
│   │   │   ├── assets/
│   │   │   └── environments/                        # Environment configuration (dev, prod, etc.)
│   │   ├── angular.json
│   │   ├── package.json
│   │   ├── tsconfig.json
│   │   └── README.md
│   │── angular-summary/           # Angular project dedicated to the summary table UI.
│   │   ├── src/
│   │   │   ├── app/
│   │   │   │   ├── summary-table/  # Contains the Angular summary table component
│   │   │   │   │   ├── summary-table.component.ts
│   │   │   │   │   ├── summary-table.component.html
│   │   │   │   │   └── summary-table.component.css
│   │   │   │   └── app.module.ts   # Module for the Angular summary app.
│   │   │   └── environments/
│   │   ├── angular.json
│   │   ├── package.json
│   │   ├── tsconfig.json
│   │   └── README.md
│   │── react-insert/              # React project for file upload/insertion.
│   │   ├── src/
│   │   │   ├── App.js
│   │   │   ├── components/
│   │   │   │   └── InsertForm.jsx    # Component that handles file upload.
│   │   │   └── index.js
│   │   ├── public/
│   │   ├── package.json
│   │   └── README.md
│   │── vue-stream/                # Vue project for viewing/streaming files.
│       ├── src/
│       │   ├── App.vue
│       │   ├── components/
│       │   │   └── FileViewer.vue    # Component for streaming/playing files.
│       │   └── main.js
│       ├── public/
│       ├── package.json
│       └── README.md
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
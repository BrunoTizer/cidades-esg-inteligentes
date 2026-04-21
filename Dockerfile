# ==================================================
# Stage 1: Builder
# ==================================================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

LABEL maintainer="Equipe ESG Inteligente"
LABEL description="Cidades ESG Inteligentes - API de Monitoramento"

WORKDIR /app

# Cache das dependências Maven antes de copiar o código
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código-fonte e executar build com testes
COPY src ./src
RUN mvn package -B

# ==================================================
# Stage 2: Production
# ==================================================
FROM eclipse-temurin:21-jre-alpine AS production

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder --chown=appuser:appgroup /app/target/*.jar app.jar

USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]

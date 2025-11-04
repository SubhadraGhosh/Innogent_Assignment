from fastapi import FastAPI
from app.core.database import  Base, engine
from app.routers import product, company, category

from app.core.logger import logger


try:
    Base.metadata.create_all(bind=engine)
    logger.info(" Database tables created successfully.")
except Exception as e:
    logger.error(f" Failed to create database tables: {e}")

# ==========================
# FastAPI App Initialization
# ==========================
app = FastAPI(title="Product Management API with MySQL")
logger.info(" FastAPI application instance created.")

# ==========================
# Routers Registration
# ==========================
try:
    app.include_router(product.router)
    logger.info(" Product router loaded successfully.")
    app.include_router(company.router)
    logger.info(" Company router loaded successfully.")
    app.include_router(category.router)
    logger.info(" Category router loaded successfully.")
except Exception as e:
    logger.error(f" Error while including routers: {e}")

# ==========================
# Root Endpoint
# ==========================
@app.get("/")
def home():
    logger.info(" '/' endpoint accessed.")
    return {"message": "Welcome to Product Management API"}



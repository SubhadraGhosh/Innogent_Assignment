from fastapi import FastAPI
from app.database import  Base, engine
from app.routers import product, company, category

# Logging setup
# logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')


# Create database tables
Base.metadata.create_all(bind=engine)


app = FastAPI(title="Product Management API with MySQL")


# Routers
app.include_router(product.router)
app.include_router(company.router)
app.include_router(category.router)


@app.get("/")
def home():
    return {"message": "Welcome to Product Management API"}
from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app.crud import crud
from app.schemas import schemas
from app.core.database import get_db
import logging

# Import logger from your logger configuration
from app.core.logger import logger

router = APIRouter(prefix="/products", tags=["Products"])


@router.post("/", response_model=schemas.Product)
def create_product(product: schemas.ProductCreate, db: Session = Depends(get_db)):
    logger.info(f"Creating new product: {product.name}")
    created_product = crud.create_product(db, product)
    logger.info(f"Product created successfully with ID: {created_product.id}")
    return created_product


@router.get("/", response_model=list[schemas.Product])
def get_all_products(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    logger.info(f"Fetching all products with skip={skip}, limit={limit}")
    products = crud.get_products(db, skip, limit)
    logger.info(f"Fetched {len(products)} products")
    return products


@router.get("/search", response_model=list[schemas.Product])
def search_products(q: str = None, company_id: int = None, skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    logger.info(f"Searching products | Query='{q}', CompanyID={company_id}, skip={skip}, limit={limit}")
    products = crud.search_products(db, q, company_id, skip, limit)
    logger.info(f"Found {len(products)} products matching search criteria")
    return products


@router.get("/{product_id}", response_model=schemas.Product)
def get_product_by_id(product_id: int, db: Session = Depends(get_db)):
    logger.info(f"Fetching product by ID: {product_id}")
    product = crud.get_product_by_id(db, product_id)
    if not product:
        logger.warning(f"Product with ID {product_id} not found")
    else:
        logger.info(f"Product found: {product.name}")
    return product


@router.delete("/{product_id}")
def delete_product(product_id: int, db: Session = Depends(get_db)):
    logger.info(f"Attempting to delete product with ID: {product_id}")
    result = crud.delete_product(db, product_id)
    if result:
        logger.info(f"Product with ID {product_id} deleted successfully")
    else:
        logger.warning(f"Failed to delete product with ID {product_id} (not found)")
    return result




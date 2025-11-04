from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.crud import crud
from app.schemas import schemas
from app.core.database import get_db
from app.core.logger import logger  # Import your custom logger

# Create router for Category
router = APIRouter(prefix="/categories", tags=["Categories"])


# Create a new category
@router.post("/", response_model=schemas.Category)
def create_category(category: schemas.CategoryCreate, db: Session = Depends(get_db)):
    logger.info(f"Request to create category: {category.name}")
    try:
        new_category = crud.create_category(db, category)
        logger.info(f"Category created successfully: {new_category.name} (ID: {new_category.id})")
        return new_category
    except Exception as e:
        logger.error(f"Error creating category '{category.name}': {e}")
        raise HTTPException(status_code=500, detail="Error creating category")


# Get all categories
@router.get("/", response_model=list[schemas.Category])
def get_categories(db: Session = Depends(get_db)):
    logger.info("Fetching all categories.")
    try:
        categories = crud.get_categories(db)
        logger.info(f"Retrieved {len(categories)} categories.")
        return categories
    except Exception as e:
        logger.error(f"Error fetching categories: {e}")
        raise HTTPException(status_code=500, detail="Error fetching categories")

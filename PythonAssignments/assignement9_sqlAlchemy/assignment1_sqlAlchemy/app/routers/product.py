from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app import crud, schemas
from app.database import get_db


router = APIRouter(prefix="/products", tags=["Products"])


@router.post("/", response_model=schemas.Product)
def create_product(product: schemas.ProductCreate, db: Session = Depends(get_db)):
    return crud.create_product(db, product)


@router.get("/", response_model=list[schemas.Product])
def get_all_products(skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
    return crud.get_products(db, skip, limit)


@router.get("/{product_id}", response_model=schemas.Product)
def get_product_by_id(product_id: int, db: Session = Depends(get_db)):
    return crud.get_product_by_id(db, product_id)


@router.delete("/{product_id}")
def delete_product(product_id: int, db: Session = Depends(get_db)):
     return crud.delete_product(db, product_id)


@router.get("/search", response_model=list[schemas.Product])
def search_products(q: str = None, company_id: int = None, skip: int = 0, limit: int = 10, db: Session = Depends(get_db)):
     return crud.search_products(db, q, company_id, skip, limit)
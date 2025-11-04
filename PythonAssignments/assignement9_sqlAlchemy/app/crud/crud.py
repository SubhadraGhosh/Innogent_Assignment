from sqlalchemy.orm import Session
from fastapi import HTTPException
from app.models import models
from app.schemas import  schemas
from sqlalchemy import or_, String

# Company CRUD
def create_company(db: Session, company: schemas.CompanyCreate):
    db_company = models.Company(**company.dict())
    db.add(db_company)
    db.commit()
    db.refresh(db_company)
    return db_company


def get_companies(db: Session):
    return db.query(models.Company).all()


# Category CRUD
def create_category(db: Session, category: schemas.CategoryCreate):
    db_category = models.Category(**category.dict())
    db.add(db_category)
    db.commit()
    db.refresh(db_category)
    return db_category


def get_categories(db: Session):
    return db.query(models.Category).all()


# Product CRUD
def create_product(db: Session, product: schemas.ProductCreate):
    existing = db.query(models.Product).filter(models.Product.name == product.name).first()
    if existing:
         raise HTTPException(status_code=400, detail="Product with this name already exists.")
    db_product = models.Product(**product.dict())
    db.add(db_product)
    db.commit()
    db.refresh(db_product)
    return db_product


def get_products(db: Session, skip: int = 0, limit: int = 10):
    return db.query(models.Product).offset(skip).limit(limit).all()


def get_product_by_id(db: Session, product_id: int):
    product = db.query(models.Product).filter(models.Product.id == product_id).first()
    if not product:
         raise HTTPException(status_code=404, detail="Product not found.")
    return product


def delete_product(db: Session, product_id: int):
     product = get_product_by_id(db, product_id)
     db.delete(product)
     db.commit()
     return {"message": "Product deleted successfully"}




def search_products(db: Session, q: str = None, company_id: int = None, skip: int = 0, limit: int = 10):
    # Step 1: Start base query
    query = db.query(models.Product)

    # Step 2: Join Category if you want to search by category name
    if q:
        query = query.join(models.Category, isouter=True)
        query = query.filter(
            or_(
                models.Product.name.ilike(f"%{q}%"),
                models.Category.name.ilike(f"%{q}%"),
                models.Product.price.cast(String).ilike(f"%{q}%")  # price converted to string for search
            )
        )

    # Step 3: Optional filter by company
    if company_id is not None:
        query = query.filter(models.Product.company_id == company_id)

    # Step 4: Pagination
    return query.offset(skip).limit(limit).all()


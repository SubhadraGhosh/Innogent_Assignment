from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from app import crud, schemas
from app.database import get_db


router = APIRouter(prefix="/companies", tags=["Companies"])


@router.post("/", response_model=schemas.Company)
def create_company(company: schemas.CompanyCreate, db: Session = Depends(get_db)):
    return crud.create_company(db, company)


@router.get("/", response_model=list[schemas.Company])
def get_companies(db: Session = Depends(get_db)):
    return crud.get_companies(db)
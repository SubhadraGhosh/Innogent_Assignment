from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from app.crud import crud
from app.schemas import schemas
from app.core.database import get_db
from app.core.logger import logger  # Import your centralized logger

router = APIRouter(prefix="/companies", tags=["Companies"])


# Create a new company
@router.post("/", response_model=schemas.Company)
def create_company(company: schemas.CompanyCreate, db: Session = Depends(get_db)):
    logger.info(f"Request to create company: {company.name}")
    try:
        new_company = crud.create_company(db, company)
        logger.info(f"Company created successfully: {new_company.name} (ID: {new_company.id})")
        return new_company
    except Exception as e:
        logger.error(f"Error creating company '{company.name}': {e}")
        raise HTTPException(status_code=500, detail="Error creating company")


# Get all companies
@router.get("/", response_model=list[schemas.Company])
def get_companies(db: Session = Depends(get_db)):
    logger.info("Fetching all companies.")
    try:
        companies = crud.get_companies(db)
        logger.info(f"Retrieved {len(companies)} companies.")
        return companies
    except Exception as e:
        logger.error(f"Error fetching companies: {e}")
        raise HTTPException(status_code=500, detail="Error fetching companies")

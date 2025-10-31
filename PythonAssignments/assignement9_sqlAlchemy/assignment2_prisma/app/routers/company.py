from fastapi import APIRouter, Depends, HTTPException, Query
from typing import List, Optional
from app import models as schemas
import app.crud as crud

# Prefix and tags updated for companies
router = APIRouter(prefix="/companies", tags=["companies"])

@router.post("/", response_model=schemas.CompanyRead)
async def create_company(payload: schemas.CompanyCreate):
    try:
        company = await crud.create_company(payload)
        return company
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/", response_model=List[schemas.CompanyRead])
async def list_companies(skip: int = 0, limit: int = 50):
    return await crud.list_companies(skip, limit)

@router.get("/{company_id}", response_model=schemas.CompanyRead)
async def get_company(company_id: int):
    company = await crud.get_company(company_id)
    if not company:
        raise HTTPException(status_code=404, detail="Company not found")
    return company

@router.put("/{company_id}", response_model=schemas.CompanyRead)
async def update_company(company_id: int, payload: dict):
    try:
        updated = await crud.update_company(company_id, payload)
        return updated
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.delete("/{company_id}")
async def delete_company(company_id: int):
    try:
        await crud.delete_company(company_id)
        return {"detail": "Deleted"}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/search", response_model=List[schemas.CompanyRead])
async def search_companies(
    q: Optional[str] = Query(None),
    skip: int = 0,
    limit: int = 10
):
    return await crud.search_companies(q=q, skip=skip, limit=limit)

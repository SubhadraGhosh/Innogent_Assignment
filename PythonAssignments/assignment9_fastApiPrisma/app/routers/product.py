# app/routers/product.py
from app.schemas import schemas
from fastapi import APIRouter, Depends, HTTPException, Query
from typing import List, Optional


import app.crud as crud

router = APIRouter(prefix="/products", tags=["products"])

@router.post("/", response_model=schemas.ProductRead)
async def create_product(payload: schemas.ProductCreate):
    try:
        p = await crud.create_product(payload)
        return p
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/", response_model=List[schemas.ProductRead])
async def list_products(skip: int = 0, limit: int = 50):
    return await crud.list_products(skip, limit)

@router.get("/{product_id}", response_model=schemas.ProductRead)
async def get_product(product_id: int):
    prod = await crud.get_product(product_id)
    if not prod:
        raise HTTPException(status_code=404, detail="Product not found")
    return prod

@router.put("/{product_id}", response_model=schemas.ProductRead)
async def update_product(product_id: int, payload: dict):
    try:
        updated = await crud.update_product(product_id, payload)
        return updated
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.delete("/{product_id}")
async def delete_product(product_id: int):
    try:
        await crud.delete_product(product_id)
        return {"detail": "Deleted"}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/search", response_model=List[schemas.ProductRead])
async def search_products(q: Optional[str] = Query(None), company_id: Optional[int] = Query(None), skip: int = 0, limit: int = 10):
    return await crud.search_products(q=q, company_id=company_id, skip=skip, limit=limit)

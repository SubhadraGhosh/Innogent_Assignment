from fastapi import APIRouter, Depends, HTTPException, Query
from typing import List, Optional
from app import models as schemas
from  app import  crud

# Prefix and tags updated for categories
router = APIRouter(prefix="/categories", tags=["categories"])

@router.post("/", response_model=schemas.CategoryRead)
async def create_category(payload: schemas.CategoryCreate):
    try:
        category = await crud.create_category(payload)
        return category
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/", response_model=List[schemas.CategoryRead])
async def list_categories(skip: int = 0, limit: int = 50):
    return await crud.list_categories(skip, limit)

@router.get("/{category_id}", response_model=schemas.CategoryRead)
async def get_category(category_id: int):
    category = await crud.get_category(category_id)
    if not category:
        raise HTTPException(status_code=404, detail="Category not found")
    return category

@router.put("/{category_id}", response_model=schemas.CategoryRead)
async def update_category(category_id: int, payload: dict):
    try:
        updated = await crud.update_category(category_id, payload)
        return updated
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.delete("/{category_id}")
async def delete_category(category_id: int):
    try:
        await crud.delete_category(category_id)
        return {"detail": "Deleted"}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

@router.get("/search", response_model=List[schemas.CategoryRead])
async def search_categories(
    q: Optional[str] = Query(None),
    skip: int = 0,
    limit: int = 10
):
    return await crud.search_categories(q=q, skip=skip, limit=limit)

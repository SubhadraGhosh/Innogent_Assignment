# app/crud.py
from prisma import Prisma
from typing import Optional, List
  # prisma model types (optional)
 import models as schemas  # pydantic schemas
from prisma_client import get_db

# Company
async def create_company(data: schemas.CompanyCreate):
    return await db.company.create({"data": data.model_dump()})

async def list_companies(skip: int = 0, limit: int = 100):
    return await db.company.find_many(skip=skip, take=limit)

async def get_company(company_id: int):
    return await db.company.find_unique(where={"id": company_id})

# Category
async def create_category(data: schemas.CategoryCreate):
    return await db.category.create({"data": data.model_dump()})

async def list_categories(skip: int = 0, limit: int = 100):
    return await db.category.find_many(skip=skip, take=limit)

async def get_category(category_id: int):
    return await db.category.find_unique(where={"id": category_id})

# Product
async def create_product(data: schemas.ProductCreate):
    # check company & category exist
    company = await db.company.find_unique(where={"id": data.company_id})
    if not company:
        raise ValueError("Company not found")
    category = await db.category.find_unique(where={"id": data.category_id})
    if not category:
        raise ValueError("Category not found")

    # duplicate check: product name for same company
    dup = await db.product.find_first(where={
        "name": data.name,
        "companyId": data.company_id
    })
    if dup:
        raise ValueError("Product already exists for this company")

    return await db.product.create({
        "data": {
            "name": data.name,
            "description": data.description,
            "price": float(data.price),
            "company": {"connect": {"id": data.company_id}},
            "category": {"connect": {"id": data.category_id}},
        },
        "include": {"company": True, "category": True}
    })

async def list_products(skip: int = 0, limit: int = 50):
    return await db.product.find_many(skip=skip, take=limit, include={"company": True, "category": True})

async def get_product(product_id: int):
    return await db.product.find_unique(where={"id": product_id}, include={"company": True, "category": True})

async def update_product(product_id: int, data):
    obj = await db.product.update(
        where={"id": product_id},
        data={
            k: (float(v) if k == "price" else v) for k, v in data.items()
        }
    )
    return obj

async def delete_product(product_id: int):
    return await db.product.delete(where={"id": product_id})

# Search: q across name, category.name, price; optional company filter; pagination
async def search_products(q: Optional[str] = None, company_id: Optional[int] = None, skip: int = 0, limit: int = 10):
    where = {}
    if q:
        # attempt numeric match for price
        price_filter = None
        try:
            price_val = float(q)
            price_filter = {"price": price_val}
        except ValueError:
            price_filter = None

        # build OR filters
        or_filters = [
            {"name": {"contains": q, "mode": "insensitive"}},
            {"category": {"is": {"name": {"contains": q, "mode": "insensitive"}}}}
        ]
        if price_filter:
            or_filters.append({"price": price_val})

        where["OR"] = or_filters

    if company_id is not None:
        # combine company filter
        if "AND" in where:
            where["AND"].append({"companyId": company_id})
        else:
            # if we already have OR, wrap them in AND with company filter
            if "OR" in where:
                where = {"AND": [ {"companyId": company_id}, {"OR": where["OR"]} ]}
            else:
                where["companyId"] = company_id

    results = await db.product.find_many(where=where if where else None, skip=skip, take=limit, include={"company": True, "category": True})
    return results

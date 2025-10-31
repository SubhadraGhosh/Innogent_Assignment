# app/models.py
from pydantic import BaseModel, Field, PositiveFloat
from typing import Optional

class CompanyBase(BaseModel):
    name: str
    description: Optional[str] = None

class CompanyCreate(CompanyBase):
    pass

class CompanyRead(CompanyBase):
    id: int
    class Config:
        from_attributes = True  # pydantic v2 style, use orm_mode for v1

class CategoryBase(BaseModel):
    name: str

class CategoryCreate(CategoryBase):
    pass

class CategoryRead(CategoryBase):
    id: int
    class Config:
        from_attributes = True

class ProductBase(BaseModel):
    name: str
    description: Optional[str] = None
    price: PositiveFloat
    company_id: int = Field(..., alias="companyId")
    category_id: int = Field(..., alias="categoryId")

class ProductCreate(ProductBase):
    pass

class ProductRead(BaseModel):
    id: int
    name: str
    description: Optional[str]
    price: float
    company_id: int
    category_id: int
    # nested optional objects
    company: Optional[CompanyRead] = None
    category: Optional[CategoryRead] = None

    class Config:
        from_attributes = True
        populate_by_name = True

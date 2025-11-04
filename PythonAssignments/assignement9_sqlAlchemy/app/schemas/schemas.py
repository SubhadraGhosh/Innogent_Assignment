from pydantic import BaseModel
from typing import Optional


class CompanyBase(BaseModel):
    name: str
    location: Optional[str]


class CompanyCreate(CompanyBase):
    pass


class Company(CompanyBase):
    id: int
    class Config:
       orm_mode = True


class CategoryBase(BaseModel):
    name: str


class CategoryCreate(CategoryBase):
    pass


class Category(CategoryBase):
    id: int
    class Config:
        orm_mode = True


class ProductBase(BaseModel):
    name: str
    price: float
    company_id: int
    category_id: int


class ProductCreate(ProductBase):
    pass


class Product(ProductBase):
    id: int
    company: Optional[Company]
    category: Optional[Category]


class Config:
    orm_mode = True
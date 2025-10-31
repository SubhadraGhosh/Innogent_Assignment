from sqlalchemy import Column, Integer, String, Float, ForeignKey
from sqlalchemy.orm import relationship
from app.database import Base


class Company(Base):
    __tablename__ = 'companies'


    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), unique=True, nullable=False)
    location = Column(String(100))


    products = relationship("Product", back_populates="company")




class Category(Base):
    __tablename__ = 'categories'


    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), unique=True, nullable=False)


    products = relationship("Product", back_populates="category")




class Product(Base):
    __tablename__ = 'products'


    id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100), nullable=False)
    category_id = Column(Integer, ForeignKey('categories.id'))
    company_id = Column(Integer, ForeignKey('companies.id'))
    price = Column(Float, nullable=False)


    category = relationship("Category", back_populates="products")
    company = relationship("Company", back_populates="products")
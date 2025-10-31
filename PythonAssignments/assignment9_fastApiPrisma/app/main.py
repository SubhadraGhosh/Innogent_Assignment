from fastapi import FastAPI

# from prisma import Prisma/
# from database import db
from app.database import db
from app.routers import company, category, product
import asyncio

app = FastAPI(title="Product Management API — Prisma + FastAPI")

@app.on_event("startup")
async def startup():
    await db.connect()
    # optional - you can check db connection here
    print("Prisma connected")

@app.on_event("shutdown")
async def shutdown():
    await db.disconnect()
    print("Prisma disconnected")

app.include_router(company.router)
app.include_router(category.router)
app.include_router(product.router)

@app.get("/")
async def root():
    return {"message": "Product Management API (Prisma) running"}


# app/main.py
import sys, os
print("Current working directory:", os.getcwd())
print("Python path:", sys.path)

from fastapi import FastAPI
# from app.prisma_client import prisma, connect, disconnect
# from prisma import Prisma

from routers import company, category, product
import asyncio

app = FastAPI(title="Product Management API — Prisma + FastAPI")

@app.on_event("startup")
async def startup():
    await Prisma.connect()
    # optional - you can check db connection here
    print("Prisma connected")

@app.on_event("shutdown")
async def shutdown():
    await Prisma.disconnect()
    print("Prisma disconnected")

app.include_router(company.router)
app.include_router(category.router)
app.include_router(product.router)

@app.get("/")
async def root():
    return {"message": "Product Management API (Prisma) running"}

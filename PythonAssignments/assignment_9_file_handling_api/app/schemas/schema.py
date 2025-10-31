from pydantic import BaseModel, EmailStr
from typing import Optional


class ClientRequest(BaseModel):
    name: str
    email: EmailStr
    about: Optional[str] = None

class ClientResponse(BaseModel):
    id: int
    name: str
    email: EmailStr
    about: Optional[str] = None
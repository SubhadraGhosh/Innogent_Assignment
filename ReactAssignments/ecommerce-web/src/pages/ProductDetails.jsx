import React, {useEffect, useState} from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios'

export default function ProductDetails(){
  const { id } = useParams()
  const [product, setProduct] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(()=>{
    setLoading(true)
    axios.get(`https://fakestoreapi.com/products/${id}`)
      .then(res => setProduct(res.data))
      .catch(err => console.error(err))
      .finally(()=> setLoading(false))
  },[id])

  if(loading) return <div className="text-center">Loading...</div>
  if(!product) return <div className="text-center text-gray-500">Product not found.</div>

  return (
    <div className="bg-white rounded-lg shadow p-6 md:flex gap-6">
      <div className="md:w-1/3 flex items-center justify-center">
        <img src={product.image} alt={product.title} className="max-h-80 object-contain" />
      </div>
      <div className="md:flex-1">
        <h1 className="text-2xl font-semibold">{product.title}</h1>
        <p className="text-sm text-gray-600 mt-2">{product.category}</p>
        <div className="mt-4 text-xl font-bold">₹{Math.round(product.price*82)}</div>
        <p className="mt-4 text-gray-700">{product.description}</p>

        <div className="mt-6 flex items-center gap-4">
          <button className="px-4 py-2 bg-blue-600 text-white rounded">Add to Cart</button>
          <button className="px-4 py-2 border rounded">Buy Now</button>
        </div>
      </div>
    </div>
  )
}

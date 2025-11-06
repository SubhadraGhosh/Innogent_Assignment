import React from 'react'
import { Link } from 'react-router-dom'

export default function ProductCard({product}){
  return (
    <Link to={`/product/${product.id}`} className="block bg-gray-200 rounded-xl shadow-md p-4 
                 hover:bg-gray-300 hover:shadow-lg transition-all">
      <div className="h-48 flex items-center justify-center">
        <img src={product.image} alt={product.title} className="max-h-40 object-contain" />
      </div>
      <h3 className="mt-3 font-medium text-sm line-clamp-2">{product.title}</h3>
      <div className="mt-2 flex items-center justify-between">
        <span className="font-semibold">₹{Math.round(product.price*82)}</span>
        <span className="text-xs text-gray-500">{product.category}</span>
      </div>
    </Link>
  )
}

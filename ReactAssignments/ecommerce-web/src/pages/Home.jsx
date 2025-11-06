import React, {useEffect, useState} from 'react'
import axios from 'axios'
import ProductCard from '../components/ProductCard'

export default function Home(){
  const [products, setProducts] = useState([])
  const [search, setSearch] = useState('')
  const [category, setCategory] = useState('all')
  const [categories, setCategories] = useState([])

  useEffect(()=>{
    axios.get('https://fakestoreapi.com/products')
      .then(res => {
        setProducts(res.data)
        const cats = Array.from(new Set(res.data.map(p=>p.category)))
        setCategories(cats)
      })
      .catch(err => console.error(err))
  },[])

  const filtered = products.filter(p=>{
    if(category !== 'all' && p.category !== category) return false
    if(search.trim() && !p.title.toLowerCase().includes(search.toLowerCase())) return false
    return true
  })

  // Group by category for display
  const grouped = filtered.reduce((acc,p)=>{
    (acc[p.category] = acc[p.category] || []).push(p)
    return acc
  }, {})

  return (
    <div>
      <div className="flex justify-center mb-10">
  <div className="flex flex-col sm:flex-row items-center gap-3">
    <input
      value={search}
      onChange={e => setSearch(e.target.value)}
      placeholder="Search products..."
      className="px-4 py-2 rounded-lg border focus:outline-none focus:ring-2 focus:ring-blue-500 w-60 sm:w-72 bg-gray-100"
    />
    <select
      value={category}
      onChange={e => setCategory(e.target.value)}
      className="px-3 py-2 rounded-lg border focus:outline-none focus:ring-2 focus:ring-blue-500 w-48 bg-gray-100"
    >
      <option value="all">All Categories</option>
      {categories.map(c => (
        <option key={c} value={c}>{c}</option>
      ))}
    </select>
  </div>
</div>


      <div className="space-y-8">
        {Object.keys(grouped).length===0 && (<div className="text-center text-gray-500">No products found.</div>)}
        {Object.entries(grouped).map(([cat, items])=>(
          <section key={cat}>
            <h2 className="text-lg font-semibold mb-4 capitalize">{cat}</h2>
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-4">
              {items.map(p => <ProductCard key={p.id} product={p} />)}
            </div>
          </section>
        ))}
      </div>
    </div>
  )
}

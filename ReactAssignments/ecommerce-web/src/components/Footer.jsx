import React from 'react'

export default function Footer(){
  return (
    <footer className="bg-gray-800 border-t">
      <div className="container mx-auto px-4 py-6 text-center text-sm text-gray-600 text-white">
        © {new Date().getFullYear()} E-Commerce Web. All rights reserved.
      </div>
    </footer>
  )
}

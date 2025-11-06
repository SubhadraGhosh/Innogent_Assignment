import React from 'react'
import { Link } from 'react-router-dom'
import Logo from '../assets/logo.svg'

export default function Header(){
  return (
<header className="bg-gray-800 shadow">

      <div className="container mx-auto px-4 py-4 flex items-center justify-between text-white">
        <Link to="/" className="flex items-center gap-3">
          <img src={Logo} alt="E-Commerce Web" className="w-10 h-10" />
          <div>
            <div className="text-xl font-bold">E-Commerce Web</div>
           
          </div>
        </Link>

        <nav className="flex items-center gap-6">
          <button aria-label="cart" title="Cart" className="flex items-center gap-2">
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2 5h12l-2-5M10 21a1 1 0 100-2 1 1 0 000 2zm6 0a1 1 0 100-2 1 1 0 000 2z"/></svg>
            <span className="hidden md:inline text-sm">Cart</span>
          </button>

          <button aria-label="notifications" title="Notifications" className="flex items-center gap-2">
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6 6 0 10-12 0v3.159c0 .538-.214 1.055-.595 1.436L4 17h11z"/></svg>
            <span className="hidden md:inline text-sm">Notifications</span>
          </button>

          <button aria-label="profile" title="Profile" className="flex items-center gap-2">
            <svg className="w-6 h-6 rounded-full" fill="currentColor" viewBox="0 0 24 24"><path d="M12 12c2.7 0 5-2.3 5-5s-2.3-5-5-5-5 2.3-5 5 2.3 5 5 5zm0 2c-3.3 0-9 1.7-9 5v2h18v-2c0-3.3-5.7-5-9-5z"/></svg>
            <span className="hidden md:inline text-sm">Profile</span>
          </button>
        </nav>
      </div>
    </header>
  )
}

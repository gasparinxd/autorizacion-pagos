import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [pagos, setPagos] = useState([])
  const [error, setError] = useState(null)

  useEffect(() => {
    fetch('/api/pagos')
      .then((res) => {
        if (!res.ok) throw new Error(`HTTP ${res.status}`)
        return res.json()
      })
      .then(setPagos)
      .catch((err) => setError(err.message))
  }, [])

  return (
    <main>
      <h1>Autorización de Pagos</h1>
      {error && <p className="error">No se pudo conectar con el backend: {error}</p>}
      {!error && pagos.length === 0 && <p>No hay pagos registrados.</p>}
      <ul>
        {pagos.map((p) => (
          <li key={p.id}>
            #{p.id} — {p.monto} {p.moneda} — {p.estado}
          </li>
        ))}
      </ul>
    </main>
  )
}

export default App

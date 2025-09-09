import Home from "./pages/Home";
import { useEffect, useMemo, useState } from "react";

export default function App() {
  const [theme, setTheme] = useState(() => localStorage.getItem("theme") || "light");

  useEffect(() => {
    const root = document.documentElement;
    if (theme === "dark") {
      root.classList.add("theme-dark");
    } else {
      root.classList.remove("theme-dark");
    }
    localStorage.setItem("theme", theme);
  }, [theme]);

  const toggle = useMemo(() => () => setTheme((t) => (t === "dark" ? "light" : "dark")), []);

  return (
    <div className="app">
      <header className="header">
        <div className="brand">
          <span>StudyBuddy</span>
          <span className="badge">DSA Hints</span>
        </div>
        <button className="button button-sm" onClick={toggle} aria-label="Toggle theme">
          {theme === "dark" ? "Light mode" : "Dark mode"}
        </button>
      </header>
      <Home />
    </div>
  );
}

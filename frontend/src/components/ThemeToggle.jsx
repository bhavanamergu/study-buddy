export default function ThemeToggle({ theme, onToggle }) {
  return (
    <button className="button" onClick={onToggle} aria-label="Toggle theme">
      {theme === "dark" ? "Light mode" : "Dark mode"}
    </button>
  );
}


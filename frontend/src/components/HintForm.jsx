import { useMemo, useState } from "react";

const BACKEND_URL = import.meta.env.VITE_API_URL || "http://localhost:9090";

const providerToEnum = (value) => {
  switch (value) {
    case "llama":
      return "LLAMA";
    case "deepseek":
      return "DEEPSEEK";
    case "gemini":
      return "GEMINI";
    default:
      return "LLAMA";
  }
};

export default function HintForm() {
  const [problemText, setProblemText] = useState("");
  const [userAttempt, setUserAttempt] = useState("");
  const [hintLevel, setHintLevel] = useState(1);
  const [provider, setProvider] = useState("llama");
  const [hint, setHint] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const disabled = useMemo(() => {
    return !problemText.trim() || loading;
  }, [problemText, loading]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setHint("");

    try {
      const response = await fetch(`${BACKEND_URL}/api/hints`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          problemText,
          userAttempt,
          hintLevel,
          provider: providerToEnum(provider),
        }),
      });

      if (!response.ok) {
        const text = await response.text();
        throw new Error(text || `HTTP ${response.status}`);
      }

      const data = await response.json();
      setHint(data.hint || "No hint returned.");
    } catch (err) {
      setError(err.message || "Failed to fetch hint.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card">
      <h2 className="section-title">Get a guided hint</h2>
      <p className="muted">Provide your problem and attempt. Choose provider and hint level.</p>
      <form className="hint-form" onSubmit={handleSubmit}>
        <div>
          <label>Problem</label>
          <textarea
            placeholder="Describe the problem statement"
            value={problemText}
            onChange={(e) => setProblemText(e.target.value)}
          />
        </div>

        <div>
          <label>Your attempt</label>
          <textarea
            placeholder="Explain what you tried, including code sketches if any"
            value={userAttempt}
            onChange={(e) => setUserAttempt(e.target.value)}
          />
        </div>

        <div className="row">
          <div>
            <label>Hint level</label>
            <select value={hintLevel} onChange={(e) => setHintLevel(Number(e.target.value))}>
              <option value={1}>Level 1 - Nudge</option>
              <option value={2}>Level 2 - Outline</option>
              <option value={3}>Level 3 - Skeleton</option>
            </select>
          </div>
          <div>
            <label>Provider</label>
            <select value={provider} onChange={(e) => setProvider(e.target.value)}>
              <option value="llama">LLaMA</option>
              <option value="deepseek">DeepSeek</option>
              <option value="gemini">Gemini</option>
            </select>
          </div>
        </div>

        <div className="controls">
          <button className="button" type="submit" disabled={disabled}>
            {loading ? "Getting hint..." : "Get Hint"}
          </button>
        </div>
      </form>

      {error && <p className="status" style={{ color: "#ef4444" }}>{error}</p>}
      {hint && (
        <div className="card" style={{ marginTop: "1rem", alignSelf: "start" }}>
          <h3 className="section-title">Hint</h3>
          <div className="output">{hint}</div>
        </div>
      )}
    </div>
  );
}

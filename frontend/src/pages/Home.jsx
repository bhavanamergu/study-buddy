import HintForm from "../components/HintForm";

export default function Home() {
  return (
    <main className="container">
      <div className="grid">
        <div className="card">
          <h2 className="section-title">How it works</h2>
          <p className="muted">
            Choose a provider, set hint level, and submit your problem. You will receive a step-by-step hint without the full solution.
          </p>
        </div>
        <HintForm />
      </div>
    </main>
  );
}

// tailwind.config.js
module.exports = {
  content: [
    "./src/cljs/**/*.cljs", // Scans your ClojureScript files
    "./resources/public/**/*.html"  // Scans your HTML files
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

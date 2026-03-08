# guestbook

## March 2026 - kick-off checklist

 In 2 terminals:

 1. Backend REPL (editor connects here):
 ```sh
   clj -M:dev:repl/conjure
 ```

 2. Frontend watch:
 ```sh
   npm run watch
 ```

 Optional:

 ```sh
   npm run tailwind:watch
 ```

 Then in Vim:
 - connect to backend nREPL → eval (go)
 - edit code → eval (reset) as needed
 - connect to shadow nREPL on localhost:7002 when doing CLJS work


--- 

Start a [REPL](#repls) in your editor or terminal of choice.

Start the server with:

```clojure
(go)
```

The default API is available under http://localhost:3000/api

System configuration is available under `resources/system.edn`.

To reload changes:

```clojure
(reset)
```

## REPLs

### Cursive

Configure a [REPL following the Cursive documentation](https://cursive-ide.com/userguide/repl.html). Using the default "Run with IntelliJ project classpath" option will let you select an alias from the ["Clojure deps" aliases selection](https://cursive-ide.com/userguide/deps.html#refreshing-deps-dependencies).

### CIDER

Use the `cider` alias for CIDER nREPL support (run `clj -M:dev:cider`). See the [CIDER docs](https://docs.cider.mx/cider/basics/up_and_running.html) for more help.

Note that this alias runs nREPL during development. To run nREPL in production (typically when the system starts), use the kit-nrepl library through the +nrepl profile as described in [the documentation](https://kit-clj.github.io/docs/profiles.html#profiles).

### Command Line

Run `clj -M:dev:nrepl` or `make repl` (see below for how we go straight from CIDER though).

Note that, just like with [CIDER](#cider), this alias runs nREPL during development. To run nREPL in production (typically when the system starts), use the kit-nrepl library through the +nrepl profile as described in [the documentation](https://kit-clj.github.io/docs/profiles.html#profiles).

### Clojurescript

I added the clojurescript kit module for this and it works pretty well.

Make sure you follow the guidance at https://kit-clj.github.io/docs/clojurescript.html.  Basically:

- Add the :kit/cljs module by running (kit/install-module :kit/cljs). Be sure to first run (kit/sync-modules) to download the modules if you haven't done that in your project yet.
- Restart your application.
- Install JavaScript dependencies by running `npm install` in your project's root directory.
- Run the shadow-cljs compiler in watch mode by executing `npx shadow-cljs watch app` (or for us we just do `npm run watch` because that's what we added to the packages.json.
- Connect to you shadow-cjls nREPL on port 7002 using your preferred editor. Yes.
- Open your project's root page (http://localhost:3000 by default) in your preferred browser.
- In the shadow-cjls REPL, run (shadow.cljs.devtools.api/repl :app). NOTE: We don't seem to need to do this and that file is not available).
- Verify that everything is wired correctly by running (js/alert "Hi") in your shadow-cljs REPL. This should display an alert in your browser window.
- You can now write your ClojureScript code by editing the core.cljs file inside the src/cljs directory.

This is how we do it. I've added a .dir-locals.el file which allows us to start the Clojure repl with Cider, without having to do it in the terminal first. Just go to `core.clj` for example and then do `cider-jack-in-clj`.

To run the Clojurescript repl, in a terminal we have to run `npm run watch` which according to the `package.json` runs `shadow-cljs watch app`. Then in Emacs you connect to its repl on port 7002 with `cider-connect-cljs` and make sure you choose `localhost`, the port and `shadow` as the options.

### Tailwindcss

Make sure you do `npm run tailwind:watch` in a terminal to compile the CSS. WARNING: when you add CSS stuff in your hiccup, if the tailwind rule is already present in `main.css` then you get hot reloading. If it's NOT in the `main.css` file already, then you have to do a browser refresh to see it. This is because we are using `kit` and the server is providing the surrounding html. If we were just using clojurescript and using the development browser (on port 8080 - check the docs), then it would hot reload.

With some AI advice, I wrote `tailwind.conf.js` when I was trying to sort out the hot-reloading, but I don't think I really need it yet. It might be useful for adding themes, etc, though.

This is a decent three videos: https://www.youtube.com/watch?v=BZNJi5pP8fU&list=PL9KxKa8NpFxKImpq9wvow5xCeMZzB840H&index=1.

<#macro layout>
    <!doctype html>
    <html lang="en" data-theme="light">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Lecture FAQ</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <link href="https://cdn.jsdelivr.net/npm/daisyui@5/themes.css" rel="stylesheet" type="text/css" />
    </head>
    <body class="min-h-screen flex flex-col bg-base-200">
    <header>
        <nav class="navbar bg-base-100 shadow-lg">
            <div class="navbar-start">
                <div class="dropdown">
                    <div tabindex="0" role="button" class="btn btn-ghost lg:hidden">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" />
                        </svg>
                    </div>
                    <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">
                        <li><a href="/app/user/lecturer/profile">Profil</a></li>
                        <li><a href="/app/user/lecturer/lectures">Meine Vorlesungen</a></li>
                        <li><a href="/app/user/lecturer/poll-form">Multiple-Choice-Fragen</a></li>
                    </ul>
                </div>
                <a href="/app/" class="btn btn-ghost text-xl">Lecture FAQ</a>
            </div>
            <div class="navbar-center hidden lg:flex">
                <ul class="menu menu-horizontal px-1">
                    <li><a href="/app/user/lecturer/profile">Profil</a></li>
                    <li><a href="/app/user/lecturer/lectures">Meine Vorlesungen</a></li>
                    <li><a href="/app/user/lecturer/poll-form">Multiple-Choice-Fragen</a></li>
                </ul>
            </div>
            <div class="navbar-end">
                <a href="/app/logout" class="btn btn-ghost">Logout</a>
            </div>
        </nav>
    </header>

    <main class="flex-grow container mx-auto px-4 py-8">
        <#nested>
    </main>

    <footer class="footer footer-center p-10 bg-base-300 text-base-content rounded">
        <nav class="grid grid-flow-col gap-4">
            <a href="#" class="link link-hover">Imprint</a>
            <a href="#" class="link link-hover">About us</a>
            <a href="#" class="link link-hover">Support</a>
        </nav>
        <aside>
            <p>Lecture FAQ © 2026 - All rights reserved</p>
        </aside>
    </footer>
    </body>
    </html>
</#macro>
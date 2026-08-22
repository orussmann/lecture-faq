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
                    <li><a href="/app/public-polls">Multiple-Choice-Fragen</a></li>
                    <li><a href="/app/public-lectures">Lectures</a></li>
                </ul>
            </div>
            <a class="btn btn-ghost text-xl">Lecture FAQ</a>
        </div>
        <div class="navbar-center hidden lg:flex">
            <ul class="menu menu-horizontal px-1">
                <li><a href="/app/public-polls">Multiple-Choice-Fragen</a></li>
                <li><a href="/app/public-lectures">Lectures</a></li>
            </ul>
        </div>
        <div class="navbar-end">
            <a class="btn btn-ghost">About</a>
        </div>
    </nav>
</header>

<main class="flex-grow container mx-auto px-4 py-8">
        <div class="hero min-h-[40vh] bg-base-200">
            <div class="hero-content text-center">
                <div class="max-w-md">
                    <h1 class="text-5xl font-bold">Welcome to Lecture FAQ</h1>
                    <p class="py-6">Making lectures more interactive and engaging for students and lecturers.</p>
                </div>
            </div>
        </div>

        <div class="grid lg:grid-cols-2 gap-8 mt-8">
            <div class="card bg-base-100 shadow-xl">
                <div class="card-body">
                    <h2 class="card-title text-2xl">Login</h2>
                    <form action="/app/login" method="post" class="space-y-4">
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">Username</span>
                            </label>
                            <input type="text" name="username" placeholder="username" class="input input-bordered w-full" />
                        </div>
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">Password</span>
                            </label>
                            <input type="password" name="password" placeholder="password" class="input input-bordered w-full" />
                        </div>
                        <div class="form-control mt-6">
                            <button class="btn btn-primary w-full">Login</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card bg-base-100 shadow-xl">
                <div class="card-body">
                    <h2 class="card-title text-2xl">Register</h2>
                    <form action="/app/register" method="post" class="space-y-4">
                        <#if success??>
                            <div class="alert alert-success">
                                <span>${success}</span>
                            </div>
                        </#if>
                        <#if error??>
                            <div class="alert alert-error">
                                <span>${error}</span>
                            </div>
                        </#if>
                        <#if notification??>
                            <div class="alert alert-info">
                                <span>${notification}</span>
                            </div>
                        </#if>
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">E-mail</span>
                            </label>
                            <input type="email" name="email" placeholder="email@example.com" class="input input-bordered w-full" required maxlength="100" />
                            <#if errors?? && errors.getFieldError("email")??>
                                <label class="label">
                                    <span class="label-text-alt text-error">${errors.getFieldError("email")["defaultMessage"]}</span>
                                </label>
                            </#if>
                        </div>
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">Password</span>
                            </label>
                            <input type="password" name="password" placeholder="password" class="input input-bordered w-full" required minlength="5" maxlength="50" />
                        </div>
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">Password Confirmation</span>
                            </label>
                            <input type="password" name="passwordConfirmation" placeholder="password confirmation" class="input input-bordered w-full" required minlength="5" maxlength="50" />
                            <#if errors?? && errors.getFieldError("passwordConfirmation")??>
                                <label class="label">
                                    <span class="label-text-alt text-error">${errors.getFieldError("passwordConfirmation")["defaultMessage"]}</span>
                                </label>
                            </#if>
                        </div>
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">First name</span>
                            </label>
                            <input type="text" name="firstName" placeholder="Max" class="input input-bordered w-full" required minlength="1" maxlength="100" />
                            <#if errors?? && errors.getFieldError("firstName")??>
                                <label class="label">
                                    <span class="label-text-alt text-error">${errors.getFieldError("firstName")["defaultMessage"]}</span>
                                </label>
                            </#if>
                        </div>
                        <div class="form-control">
                            <label class="label">
                                <span class="label-text">Last name</span>
                            </label>
                            <input type="text" name="lastName" placeholder="Mustermann" class="input input-bordered w-full" required minlength="1" maxlength="100" />
                            <#if errors?? && errors.getFieldError("lastName")??>
                                <label class="label">
                                    <span class="label-text-alt text-error">${errors.getFieldError("lastName")["defaultMessage"]}</span>
                                </label>
                            </#if>
                        </div>
                        <div class="form-control mt-6">
                            <button class="btn btn-secondary w-full">Register</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
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

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lecture FAQ</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<header>
    <nav>
        <a href="/app/public-polls">Multiple-Choice-Fragen</a>
        <a href="/app/public-lectures">Lectures</a>
    </nav>
</header>
<hr>
<h2>Login</h2>
<form action="/app/login" method="post">
    <label>
        Username
        <input type="text" name="username" placeholder="username">
    </label>
    <label>
        Password
        <input type="password" name="password" placeholder="password">
    </label>
    <button>Login</button>
</form>
<br>
<h2>Register</h2>
<hr>
<form action="/app/register" method="post">
    <#if success??>
        ${success}
    </#if>
    <br>
    <label>
        E-mail
        <input type="text" name="email" placeholder="username">
    </label>
    <#if errors?? && errors.getFieldError("email")??>
        ${errors.getFieldError("email")["defaultMessage"]}
    </#if>
    <label>
        Password
        <input type="password" name="password" placeholder="password">
    </label>
    <#--<#if errors?? && errors.getFieldError("password")??>
        ${errors.getFieldError("password")["defaultMessage"]}
    </#if>-->
    <label>
        Password Confirmation
        <input type="password" name="passwordConfirmation" placeholder="password confirmation">
    </label>
    <#if errors?? && errors.getFieldError("passwordConfirmation")??>
        ${errors.getFieldError("passwordConfirmation")["defaultMessage"]}
    </#if>
    <#if error??>
        <p style="color: red">${error}</p>
    </#if>
    <#if notification??>
        ${notification}
    </#if>

    <br>

    <label>
        First name
        <input type="text" name="firstName" placeholder="Max">
    </label>
    <#if errors?? && errors.getFieldError("firstName")??>
        ${errors.getFieldError("firstName")["defaultMessage"]}
    </#if>
    <label>
        Last name
        <input type="text" name="lastName" placeholder="Mustermann">
    </label>
    <#if errors?? && errors.getFieldError("lastName")??>
        ${errors.getFieldError("lastName")["defaultMessage"]}
    </#if>
    <button>Register</button>
</form>
<footer>
    <a href="#">Imprint</a>
    <a href="#">About us</a>
    <a href="#">Support</a>
</footer>
</body>
</html>

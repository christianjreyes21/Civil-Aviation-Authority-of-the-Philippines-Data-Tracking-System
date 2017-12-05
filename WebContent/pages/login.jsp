<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN</title>
<link rel="stylesheet" href="../css/style1.css">
</head>

<body>

	<hgroup>
		<h1>
			<img src="../img/logo1.png" />
		</h1>
		<h3>STAFF LOGIN</h3>
	</hgroup>
	<form action="processlogin.html" method="post">
		<div class="group">
			<input type="text" name="username" required><span class="highlight"></span><span
				class="bar"></span> <label>Username</label>
		</div>
		<div class="group">
			<input type="password" name="password" required><span class="highlight"></span><span
				class="bar"></span> <label>Password</label>
		</div>
		<button type="submit" class="button buttonBlue">
			Log In
			<div class="ripples buttonRipples">
				<span class="ripplesCircle"></span>
			</div>
		</button>
	</form>
	<footer>
		<a href="http://www.polymer-project.org/" target="_blank"><img
			src="../img/p-logo.svg"></a>
		<p>
			If you don't have an account yet?  <a href="register.html"
				target="_blank">Sign Up</a>
		</p>
	</footer>
	<script src='../js/jquery.min.js'></script>

	<script src="../js/login.js"></script>




</body>
</html>

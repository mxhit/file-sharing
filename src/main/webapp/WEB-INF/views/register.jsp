<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Register</title>
		<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--===============================================================================================-->
		<link rel="icon" type="image/png" href="resources/images/favicon.ico"/>
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/vendor/bootstrap/css/bootstrap.min.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/vendor/animate/animate.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/vendor/css-hamburgers/hamburgers.min.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/vendor/animsition/css/animsition.min.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/vendor/select2/select2.min.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/vendor/daterangepicker/daterangepicker.css">
	<!--===============================================================================================-->
		<link rel="stylesheet" type="text/css" href="resources/css/util.css">
		<link rel="stylesheet" type="text/css" href="resources/css/main.css">
	<!--===============================================================================================-->		
	</head>
	<body>
		<div class="limiter">
			<div class="container-login100" style="background-color: white;">
				<div class="wrap-login100 p-t-30 p-b-50">
					<span class="login100-form-title p-b-41">
						<a href="./login">Already have an account, Sign in</a>
					</span>
					<form action="./register" method="POST" class="login100-form validate-form p-b-33 p-t-5">
						<!-- Full Name -->
						<div class="wrap-input100 validate-input" data-validate = "Enter fullname">
							<input class="input100" type="text" name="fullname" placeholder="Fullname" autocomplete="off">
							<span class="focus-input100" data-placeholder="&#xe82a;"></span>
						</div>
						
						<!-- Email -->
						<div class="wrap-input100 validate-input" data-validate = "Enter email">
							<input class="input100" type="email" name="email" placeholder="Email" autocomplete="off">
							<span class="focus-input100" data-placeholder="&#xe82a;"></span>
						</div>						
						
						<!-- Username -->
						<div class="wrap-input100 validate-input" data-validate = "Enter username">
							<input class="input100" type="text" name="username" placeholder="Username" autocomplete="off">
							<span class="focus-input100" data-placeholder="&#xe82a;"></span>
						</div>
	
						<!-- Password -->
						<div class="wrap-input100 validate-input" data-validate="Enter password">
							<input class="input100" type="password" name="password" placeholder="Password">
							<span class="focus-input100" data-placeholder="&#xe80f;"></span>
						</div>
	
						<!-- Login button -->
						<div class="container-login100-form-btn m-t-32">
							<button class="login100-form-btn" onclick="register()">
								Register
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	
	<!--===============================================================================================-->
		<script src="resources/vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
		<script src="resources/vendor/animsition/js/animsition.min.js"></script>
	<!--===============================================================================================-->
		<script src="resources/vendor/bootstrap/js/popper.js"></script>
		<script src="resources/vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
		<script src="resources/vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
		<script src="resources/vendor/daterangepicker/moment.min.js"></script>
		<script src="resources/vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
		<script src="resources/vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
		<script src="https://kit.fontawesome.com/c19e7ec70a.js"></script>
	<!--===============================================================================================-->
		<script src="resources/js/main.js"></script>				
	</body>
</html>
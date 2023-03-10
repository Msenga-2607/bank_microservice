<!doctype html>
<html lang="en">

<head>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
    integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

  <style>
    .card {
      width: 400px;
    }
    .card-header {
      background-color: #1E90FF;
      color: white;
      text-align: center;
    }
    .card-body {
      background-color: #ADD8E6;
    }
  </style>

  <title>Bank System | Login</title>
</head>

<body>
  <div class="row mt-3">
    <div class="col-3 mx-auto">
        <center>
            <img src="https://i.pinimg.com/originals/2c/1d/86/2c1d862b192eec296fcb2a3fd7fe820b.jpg" alt="logo"
        width="" height="150"></center>
        <h2 align="center">Bank management System</h2>
        <div class="card">
            <div class="card-header">
                Login
            </div>
            <div class="card-body">
                <form action="userloginvalidate" method="post">
                    <div class="form-group">
                        <label for="exampleInputEmail1">Username</label>
                        <input type="text" name="username" class="form-control" placeholder="Username">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" name="password" class="form-control" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </div>
  </div>

  <!-- Optional JavaScript -->
  <!-- jQuery first, then Popper.js, then Bootstrap JS -->
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
    integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
    crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/demo.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css">
    <title>create invoice</title>
</head>
<body>

<div class="site-wrapper">
    <div class="site-wrapper-inner">
        <div class="cover-container">
            <div class="inner cover">
                <h1 class="cover-heading">Create an invoice:</h1>

                <form class='form-inline' action="Index" method="post">
                    <div class="form-group">
                        <select id="id_currency" class="form-control" name="currency">
                            <option value="USD">USD</option>
                            <option value="AED">AED</option>
                            <option value="BHD">BHD</option>
                            <option value="DZD">DZD</option>
                            <option value="EGP">EGP</option>
                            <option value="IQD">IQD</option>
                            <option value="JOD">JOD</option>
                            <option value="KWD">KWD</option>
                            <option value="LBP">LBP</option>
                            <option value="MAD">MAD</option>
                            <option value="OMR">OMR</option>
                            <option value="QAR">QAR</option>
                            <option value="SAR">SAR</option>
                            <option value="TND">TND</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input id="id_amount" class="form-control" name="amount" step="any" type="number"
                               placeholder="10" required/>
                    </div>
                    <div class="form-group">
                        <select id="id_type" class="form-control" name="type">
                            <option value="cart" selected="selected">Embedded Cart</option>
                            <option value="fullscreen">Fullscreen Cart</option>
                            <option value="link">Link</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-default">Create</button>
                </form>

            </div>
            
            <br />
            
            <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissable">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <strong>${error}</strong> 
                    </div>
            </c:if>

            <div class="mastfoot">
                <div class="inner">
                    <p>Demo powered by <a href="https://yellowpay.co">Yellow</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="center-block" style="width:350px;"></div>
    </div>
</div>
    
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>

<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>invoice page</title>
    <script>
        function invoiceListener(event) {
            switch (event.data) {
                case "authorizing":
                    // Handle the invoice status update
                    document.getElementById("status").innerHTML=event.data;
                    alert("your payment is authorizing");
                    window.location = "Status?id=${invoice_id}";
                    break;
                case "expired":
                case "refund_requested":
                    alert(event.data + " status");
                    break;
            }
        }
        // Attach the message listener
        if (window.addEventListener) {
            addEventListener("message", invoiceListener, false)
        } else {
            attachEvent("onmessage", invoiceListener)
        }
    </script>
</head>
<body>
    <iframe src="${invoice_url}" style="width:393px; height:220px; overflow:hidden; border:none; margin:auto; display:block;"  scrolling="no" allowtransparency="true" frameborder="0"></iframe>
    <div align="left" style="width:393px; overflow:hidden; border:none; margin:auto; display:block; color: black; text-decoration: none; font-family: Arial, sans-serif; font-style: italic;">
        <span style="padding-left: 10px">Invoice status: <span id="status">new</span></span>
    </div>
    <div align="center" style="width:393px; height:220px; overflow:hidden; border:none; margin:auto; display:block; padding-top:30px;">
        <a style="color: black;text-decoration: none; font-family: Arial, sans-serif; font-weight: bold;" href="index.php">New invoice</a>
    </div>
</body>
</html>
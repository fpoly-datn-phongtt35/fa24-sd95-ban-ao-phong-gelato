<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/san-pham/hien-thi">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Dropdown
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="/ctsp/hien-thi">Sản phẩm chi tiết</a></li>
                        <li><a class="dropdown-item" href="/danh-muc/hien-thi">Danh mục</a></li>
                        <li><a class="dropdown-item" href="/hoa-don/hien-thi">Hóa đơn</a></li>
                        <li><a class="dropdown-item" href="/hdct/hien-thi">Hóa đơn chi tiet</a></li>
                        <li><a class="dropdown-item" href="/khach-hang/hien-thi">Khách hàng</a></li>
                        <li><a class="dropdown-item" href="/mau-sac/hien-thi">Màu sắc</a></li>
                        <li><a class="dropdown-item" href="/size/hien-thi">Size</a></li>
                    </ul>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>

<form action="/san-pham/update" method="post">
    <div class="mb-3">
        <label class="form-label">id</label>
        <input type="text" class="form-control" name="id" value="${sanPham.id}" readonly>
    </div>
    <div class="mb-3">
        <label class="form-label">Ma san pham</label>
        <input type="text" class="form-control" name="ma_san_pham" value="${sanPham.ma_san_pham}">
    </div>
    <div class="mb-3">
        <label class="form-label">Ten san pham</label>
        <input type="text" class="form-control" name="ten_san_pham}" value="${sanPham.ten_san_pham}">
    </div>
    <div class="row">
        <p class="col-4"> Trang thai
        </p>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Active" name="trang_thai"
            ${sanPham.trang_thai == 'Active' ? 'checked' :''}
            >
            <label class="form-check-label">
                Active
            </label>
        </div>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Inactive" name="trang_thai"
            ${sanPham.trang_thai == 'Inactive' ? 'checked' :''}
            >
            <label class="form-check-label">
                Inactive
            </label>
        </div>
    </div>
    <div class="mb-3">
        <label for="disabledSelect" class="form-label">Ten danh muc</label>
        <select id="disabledSelect" class="form-select" name="danhMuc">
            <c:forEach items="${lopDM}" var="dm">
                <option value="${dm.id}"
                    ${dm.id == sanPham.danhMuc.id ? 'selected' :''}
                >${dm.ten_danh_muc}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="btn btn" style="background-color: chocolate">Submit</button>
</form>
</body>
</html>
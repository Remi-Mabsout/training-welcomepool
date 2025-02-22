<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="fr">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>When Is My Code Review?</title>

    <!-- Bootstrap CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/jquery.dataTables.min.css" rel="stylesheet">


    <!-- Custom CSS -->
    <link href="css/style.css" rel="stylesheet">



    <!-- Custom Fonts -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
</head>

<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top container-fluid" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index">When Is My Code Review?</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle navlink" data-toggle="dropdown" href="#">
                    <i class="fa fa-gear fa-fw"></i> Gérer les code reviews <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="addProm"><i class="fa fa-users fa-fw"></i> Ajouter une promotion</a>
                    </li>
                    <li><a href="addmem"><i class="fa fa-user fa-fw"></i> Ajouter un membre</a>
                    </li>
                    <li><a href="addReview"><i class="fa fa-calendar fa-fw"></i> Créer un rendez-vous</a>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>

    <div id="page-wrapper" class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Panneau d'administration</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-4 col-md-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-users fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"><c:out value="${promSize}"/></div>
                                <div class="huge-label">Promotions</div>
                            </div>
                        </div>
                    </div>
                    <a href="addProm">
                        <div class="panel-footer">
                            <span class="pull-left">Ajouter une promotion</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-4 col-md-4">
                <div class="panel panel-green">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <i class="fa fa-user fa-5x"></i>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"><c:out value="${memSize}"/></div>
                                <div class="huge-label">Membres inscrits</div>
                            </div>
                        </div>
                    </div>
                    <a href="addmem">
                        <div class="panel-footer">
                            <span class="pull-left">Ajouter un membre</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-lg-4 col-md-4">
                <div class="panel panel-yellow">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-xs-3">
                                <a href="indexReview" class="nostyle">
                                    <i class="fa fa-calendar fa-5x"></i>
                                </a>
                            </div>
                            <div class="col-xs-9 text-right">
                                <div class="huge"><c:out value="${revSize}"/></div>
                                <div class="huge-label">Code reviews programmées</div>
                            </div>
                        </div>
                    </div>
                    <a href="addReview">
                        <div class="panel-footer">
                            <span class="pull-left">Ajouter une code review</span>
                            <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
                            <div class="clearfix"></div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-8">
                <!-- /.panel -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-user fa-fw"></i> Gestion des membres
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="table-responsive">
                                    <table id="dtMembers" lass="table table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Nom</th>
                                            <th>Email</th>
                                            <th>Promotion</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody id="memberBody">
                                        <c:forEach items="${members}" var="member">
                                            <tr>
                                                <form method="post" action="${pageContext.request.contextPath}/addmem">
                                                    <td><c:out value="${member.name}"/></td>
                                                    <input type="hidden" value="${member.email}" name="email">
                                                    <input type="hidden" value="${member.id}" name="id">
                                                    <input type="hidden" value="${member.classId}" name="cid">
                                                    <input type="hidden" value="${member.name}" name="name">
                                                    <input type="hidden" value="${member.birthdate}" name="naissance">
                                                    <td><c:out value="${member.email}"/> </td>
                                                    <td><c:out value="${member.promotion}"/> </td>
                                                    <td>
                                                        <button name="modifMem" type="submit" class="btn btn-sm btn-warning"><i class="fa fa-pencil"></i> Modifier</button>
                                                        <button name="deleteMem" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Supprimer</button>
                                                        <c:choose>
                                                            <c:when test="${member.classId != -1}">
                                                                <button name="deletePromo" class="btn btn-sm btn-danger"><i class="fa fa-trash"></i> Supprimer la Promo</button>
                                                             </c:when>
                                                         <c:otherwise>

                                                         </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </form>
                                            </tr>
                                        </c:forEach>
                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.table-responsive -->
                            </div>
                        </div>
                        <!-- /.row -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-8 -->
            <div class="col-lg-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-calendar fa-fw"></i> Codes reviews programmées
                    </div>
                    <div class="panel-body">
                        <table class="table table-striped">
                            <c:forEach items="${sh_reviews}" var="sh_reviews">
                                <tr>
                                    <td><c:out value="${sh_reviews.name}"/></td>
                                    <td><c:out value="${sh_reviews.promotion}"/></td>
                                    <td class="text-right"><span class="text-muted small"><c:out value="${sh_reviews.date}"/></span></td>
                                </tr>
                            </c:forEach>
                        </table>
                        <a href="addReview" class="btn btn-default btn-block">Programmer une code review</a>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-users fa-fw"></i> Gestion des promotions
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div class="list-group">
                            <c:forEach items="${sh_promo}" var="sh_promo">
                                <form method="post" action="${pageContext.request.contextPath}/addProm">
                                    <button class="list-group-item" name="modifProm">
                                        <i class="fa fa-users fa-fw"></i> <c:out value="${sh_promo.name}"/>
                                        <span class="pull-right text-muted small"><em><c:out value="${sh_promo.nbMembres}"/> membre(s)</em>
                                     </button>
                                    <input type="hidden" value="${sh_promo.name}" name="name">
                                    <input type="hidden" value="${sh_promo.id}" name="id">


                                    </span>
                                    </a>
                                </form>
                            </c:forEach>
                        </div>
                        <!-- /.list-group -->
                        <a href="addProm" class="btn btn-default btn-block">Créer une nouvelle promotion</a>
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-4 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<footer class="footer">
    <div class="container">
        <div class="row text-center">
            <img src="img/ebusiness.png" class="logo" alt=""> &bullet; 2017
        </div>
    </div>
</footer>

<!-- jQuery -->
<script src="js/jquery-3.1.1.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<script type="module" src="js/index.js"></script>

</body>

</html>

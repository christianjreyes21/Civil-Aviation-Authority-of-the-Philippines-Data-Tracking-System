<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="mark" type="java.lang.String" scope="request"/>
<jsp:useBean id="event" type="java.sql.ResultSet" scope="request"/>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Civil Aviation Authority of the Philippines</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

	<!-- DataTables CSS -->
    <link href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">

    <!-- DataTables Responsive CSS -->
    <link href="../bower_components/datatables-responsive/css/dataTables.responsive.css" rel="stylesheet">
    
    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Civil Aviation Authority of the Philippines</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="settings.html"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="tables.html"><i class="fa fa-table fa-fw"></i> Records</a>
                        </li>
                        <li>
                            <a href="forms.html"><i class="fa fa-edit fa-fw"></i> Add New Record</a>
                        </li>
                        
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">View / Edit Record</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Control Number: ${record.id}
                        </div>
                        <div class="panel-body">
                            <div class="row">
                            	<form action="acceptrecord.html" method="post" role="form" onsubmit="return confirm('Do you really want to submit the form?');">
                            	<input type="hidden" name="id" value="${record.id}"/>
                                <div class="col-lg-6">
                                    	<div class="form-group">
                                            <label>Mark</label>
                                            <% 
										    String first="";
                                            String second="";
                                            String third="";
                                            String fourth="";
                                            String fifth="";
                                            if(mark.equals("Released / Approved"))
                                            	first="selected";
                                            if(mark.equals("Released / DisApproved"))
                                            	second="selected";
                                            if(mark.equals("Pending"))
                                            	third="selected";
                                            if(mark.equals("Returned"))
                                            	fourth="selected";
                                            if(mark.equals("Released / for Action"))
                                            	fifth="selected";
											%>
                                            <select name="mark" class="form-control">
                                                <option <%=first%>>Released / Approved</option>
                                                <option <%=second%>>Released / DisApproved</option>
                                                <option <%=third%>>Pending</option>
                                                <option <%=fourth%>>Returned</option>
                                                <option <%=fifth%>>Released / for Action</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Company</label>
                                            <input name="company" value="${record.company}" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Destination</label>
                                            <input name="destination" value="${record.destination}" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Author</label>
                                            <input name="author" value="${record.author}" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Received by</label>
                                            <input name="receivedBy" value="${record.receivedBy}" class="form-control">
                                        </div>
                                </div>
                                <!-- /.col-lg-6 (nested) -->
                                <div class="col-lg-6">
                                   		<div class="form-group">
                                            <label>Date In (Latest)</label>
                                            <input name="dateIn" value="${record.dateInLatest}" type="date" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Date In (Original/Others)</label>
                                            <input name="dateInOriginal" value="${record.dateInOriginal}" type="date" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Date Out</label>
                                            <input name="dateOut" value="${record.dateOut}" type="date" class="form-control">
                                        </div>
                                        <div class="form-group">
                                            <label>Remarks</label>
                                            <textarea name="remarks" class="form-control" rows="3">${record.remarks}</textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Subject</label>
                                            <textarea name="subject" class="form-control" rows="3">${record.subject}</textarea>
                                        </div>
                                        <div class="col-lg-12" align="right">
                                        <button  type="submit" class="btn btn-default">Update</button>
                                        </div>
                                    
                                </div>
                                </form>
                                <!-- /.col-lg-6 (nested) -->
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Event Log</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Event ID</th>
                                            <th>Timestamp</th>
                                            <th>Username</th>
                                            <th>Name of the User</th>
                                            <th>Destination</th>
                                            <th>Remarks</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <% 
									while(event.next()) {
									%>
										<tr>
											<td align="right"><%=event.getString("logID")%></td>
											<td><%=event.getString("timestamp")%></td>
											<td><%=event.getString("username")%></td>
											<td><%=event.getString("name")%></td>
											<td><%=event.getString("destination")%></td>
											<td><%=event.getString("remarks")%></td>
										</tr>
									
									<% } %>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                            <div class="well">
                                <h4>Event Log Information</h4>
                                <p>An event is recorded if a user updates an existing record. Listed events are shown by their timestamps.</p>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    
    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>
    
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: true
        });
    });
    </script>

</body>

</html>

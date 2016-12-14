<?php 
$response = [];
$cp_id_proveedor = 0;
if (!empty($_POST)) { 
    $username = "ReadApp"; 
    $password = "MarketPymesApp"; 
    $servername = "localhost";
    $dbname = $_POST['DB_name'];
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql1 = $conn->prepare("select a.cp_id_proveedor_919819828828, a.cp_proveedor_razon_social_92939898234, b.cp_id_proveedor_919819828828, 
        b.cp_proveedor_codigo_92939898234
        from lp_proveedor_razon_social_i092002992 a, lp_proveedor_codigo_i092002992 b
        where ((a.cp_proveedor_razon_social_92939898234 LIKE :valor or b.cp_proveedor_codigo_92939898234 LIKE :valor) and
        (b.cp_id_proveedor_919819828828=a.cp_id_proveedor_919819828828))");
        $sql1->execute(array('valor' => $_POST['valor']));
        $resultado1 = $sql1->fetchAll();

        foreach ($resultado1 as $row) {
            $cp_id_proveedor=$row['cp_id_proveedor_919819828828'];
            $cp_proveedor_razon_social=$row['cp_proveedor_razon_social_92939898234'];
            $cp_proveedor_codigo=$row['cp_proveedor_codigo_92939898234'];

            $sql2 = $conn->prepare("select cp_id_factura_993284234, cp_cantidad_meses_098982222, cp_id_proveedor_919819828828, cp_fechas_98283748234, 
                cp_fechas_pago_98238723487
                from lp_creditos_compras_99829383
                where cp_id_proveedor_919819828828= :id_proveedor
                order by cp_fechas_pago_98238723487 asc");
            $sql2->execute(array('id_proveedor' => $cp_id_proveedor));
            $resultado2 = $sql2->fetchAll();

            foreach ($resultado2 as $row) {
                $n_factu=$row['cp_id_factura_993284234'];
                $cp_cantidad_meses=$row['cp_cantidad_meses_098982222'];
                $id_cliente=$row['cp_id_proveedor_919819828828'];
                $fechas=$row['cp_fechas_98283748234'];
                $dias_pago=$row['cp_fechas_pago_98238723487'];

                $sql3 = $conn->prepare("select cp_monto_98237487234 from lp_abonos_compras_9928398432 where cp_numero_factura_9829384= :n_factura");
                $sql3->execute(array('n_factura' => $n_factu));
                $resultado3 = $sql3->fetchAll();

                $monto_abonado=0;
                $abono_monto_total=0;
                foreach ($resultado3 as $row) {
                    $monto_abonado=$row['cp_monto_98237487234'];
                    $abono_monto_total=$abono_monto_total+$monto_abonado;    
                }
                $total_abono = number_format($abono_monto_total, 2);

                $sql4 = $conn->prepare("select * from lp_factura_compras_i092002992 where cp_numero_factura_919819828828 = :n_fact");
                $sql4 -> execute(array('n_fact' => $n_factu));
                $resultado4 = $sql4 -> fetchAll();

                foreach ($resultado4 as $row) {
                    $id_fact=$row['id'];
                }


                $sql5 = $conn->prepare("select 
                    f.id, f.cp_id_company_919819828828,
                    f.cp_activar_factura_919819828828, f.cp_id_factura_993284234,
                    f.cp_tipo_factura_92384, fc.cp_id_producto_919819828828,
                    fc.cp_cantidad_productos_9872384,p.cp_producto_codigo_92939898234,
                    pn.cp_producto_nombre_92939898234, ch.cp_id_costo_92389483984,
                    ch.cp_producto_costo_historial_92939898234
        
                    from 
                    lp_factura_compras_i092002992 f,
                    lp_facturacion_compras_i092002992 fc,
                    lp_productos_codigo_i092002992 p,
                    lp_producto_nombre_i092002992 pn,
                    lp_productos_costos_historial_i092002992 ch

                    where 
                    f.id= :id_fact and
                    fc.cp_id_factura_993284234 =f.cp_id_factura_993284234 and
                    p.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and
                    pn.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828 and
                    ch.cp_id_producto_919819828828 = fc.cp_id_producto_919819828828
        
                    order by fc.id desc");
                $sql5->execute(array('id_fact' => $id_fact));
                $resultado5 = $sql5->fetchAll();

                $total_final = 0;
                foreach ($resultado5 as $row) {
                    $id_producto=$row['cp_producto_codigo_92939898234'];
                    $id_p=$row['id'];
                    $name_producto=$row['cp_producto_nombre_92939898234'];
                    $tipo_facturas=$row['cp_tipo_factura_92384'];
                    //Precio
                    $costo_producto=$row['cp_producto_costo_historial_92939898234'];
                    $id_pp=$row['cp_id_producto_919819828828'];
                    $name_producto=$row['cp_producto_nombre_92939898234'];
                    $id_factura=$row['cp_id_factura_993284234'];
                    $id_fac=$row['cp_id_factura_993284234'];
                    $cantidad_producto=$row['cp_cantidad_productos_9872384'];
                    $total_producto=$cantidad_producto*$costo_producto;
                    $total_final=$total_final+$total_producto;   
                }

                $cuota = $total_final/$cp_cantidad_meses;
    
                $saldo = $total_final - $abono_monto_total;
                $saldo = number_format($saldo, 2);
                      
                $total_final = number_format($total_final, 2);
                $cuota = number_format($cuota, 2);

                $responseTmp;
                $responseTmp["id_factura"] = $n_factu;
                $responseTmp["fechas_pago"] = $dias_pago;
                $responseTmp["proveedor"] = $cp_proveedor_razon_social;
                $responseTmp["deuda"] = $total_final;
                $responseTmp["cuota"] = $cuota;
                $responseTmp["abonado"] = $total_abono;
                $responseTmp["saldo"] = $saldo;

                array_push($response, $responseTmp);
                }
            }
        
        echo json_encode($response);
    }
    catch(PDOException $e) {
        echo "ERROR: " . $e->getMessage();
        die(json_encode($response));
    }
}
?>

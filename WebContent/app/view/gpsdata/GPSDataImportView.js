/**
 * GPS信息导入
 */
Ext.define('ivp.view.gpsdata.GPSDataImportView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.gpsdataimportview',
    itemId : 'gpsdataimportviewId',

    init : function() {
        this.launcher = {
            text : '导入GPS信息',
            iconCls : 'icon-grid'
        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('gpsdataimportviewId');
        if (!win) {

            // var vStore = Ext.create('Ext.data.Store', {
            // // model : 'ivp.model.VehicleModel',
            // fields : [{
            // name : 'id',
            // type : 'auto'
            // }, {
            // name : 'vName',
            // type : 'auto'//
            // }, {
            // name : 'vCode'
            // }, {
            // name : 'teamName'
            // }, {
            // name : 'vLength'
            // }, {
            // name : 'vWidth'
            // }, {
            // name : 'vHeight'
            // }],
            // autoLoad : true,
            // proxy : {
            // type : 'ajax',
            // url : 's/v/all',
            // reader : {
            // type : "json",
            // root : "data",
            // successProperty : 'success',
            // totalProperty : 'total'
            // }
            // }
            // });

            var vStore = Ext.create('Ext.data.Store', {
                fields : ['id', 'vehicleID', 'vid', 'vName', 'vCode', 'vLength', 'vWidth', 'gpsEID', 'gpseid', 'gpsEName', 'eName', 'gpsECode', 'eCode', 'testedTimes', 'testing',
                        'oid', 'oName', 'x', 'y'],

                autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/tstv/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            win = desktop.createWindow({
                id : 'gpsdataimportviewId',
                title : '导入GPS信息',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'border',

                height : 450,
                width : 700,
                items : [{
                    xtype : 'gridpanel',
                    title : '车辆信息',
                    region : 'west',
                    width : 210,
                    split : true,
                    // height : 180,
                    // region : 'north',
                    store : vStore,
                    viewConfig : {
                        listeners : {
                            render : me.initializeDragVehicle
                        }
                    },
                    enableDragDrop : true,
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'vName',
                        text : '车辆名称'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'vCode',
                        text : '车牌号码'
                    }]

                }, {
                    xtype : 'form',
                    // height : 240,
                    region : 'center',
                    // autoScroll : true,
                    title : '车辆',
                    items : [{
                        xtype : 'textfield',
                        hidden : true,
                        allowBlank : false,
                        name : 'id'
                    }, {
                        xtype : 'textfield',
                        hidden : true,
                        name : 'vid'
                    }, {
                        xtype : 'textfield',
                        fieldLabel : '车辆名称',
                        anchor : '90%',
                        readOnly : true,
                        name : 'vName'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : '车牌号码',
                        name : 'vCode'
                    }, {
                        xtype : 'numberfield',
                        anchor : '90%',
                        decimalPrecision : 5,
                        readOnly : true,
                        fieldLabel : '车辆长度',
                        name : 'vLength'
                    }, {
                        xtype : 'numberfield',
                        anchor : '90%',
                        readOnly : true,
                        decimalPrecision : 5,
                        fieldLabel : '车辆宽度',
                        name : 'vWidth'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : 'GPS设备距车头距离(cm)',
                        name : 'aa'
                    }, {
                        xtype : 'textfield',
                        anchor : '90%',
                        readOnly : true,
                        fieldLabel : 'GPS设备距车左侧距离(cm)',
                        name : 'bb'
                    }, {
                        xtype : 'filefield',
                        margin : '20 0 0 0 ',
                        anchor : '90%',
                        allowBlank : false,
                        name : 'gpsDataFile',
                        fieldLabel : '<font color="red">*</font>GPS数据文件',
                        buttonText : '选择文件',
                        clearOnSubmit : false
                    }, {
                        xtype : 'datefield',
                        anchor : '90%',
                        format : 'Y-m-d H:i:s',
                        allowBlank : false,
                        fieldLabel : '测试开始时间',
                        value : new Date(),
                        name : 'startTrackTime'
                    }, {
                        xtype : 'datefield',
                        anchor : '90%',
                        format : 'Y-m-d H:i:s',
                        fieldLabel : '测试结束时间',
                        value : new Date(),
                        allowBlank : false,
                        name : 'stopTrackTime'
                    // }, {
                    // xtype: 'timefield',
                    // name: 'in',
                    // fieldLabel: 'Time In',
                    // minValue: '6:00 AM',
                    // maxValue: '8:00 PM',
                    // increment: 30,
                    // anchor: '100%'
                    }],
                    listeners : {
                        render : me.initializeDropVehicle
                    }
                }],

                buttons : [{
                    xtype : 'button',
                    text : '保存',
                    listeners : {
                        click : {
                            fn : me.onSubmit,
                            scope : me
                        }
                    }
                }, {
                    xtype : 'button',
                    text : '关闭',
                    listeners : {
                        click : {
                            fn : me.onCancelButtonClick,
                            scope : me
                        }
                    }
                }]
            });
        }
        return win;
    },

    // 关闭按钮
    onCancelButtonClick : function(btn) {
        btn.up('window').close();
    },

    /**
     * 提交
     */
    onSubmit : function(btn, e, eOpts) {
        var view = this;
        // 先找到 form这个控件, 从btn 控件开始向上查找
        var formCmp = btn.up('window').down('form');
        var form = formCmp.getForm();
        if (form.isValid()) {

        } else {
            Ext.MessageBox.alert('', '请检查必填项');
            return false;
        }

        var values = form.getValues();
        // 如果有id 说明是修改
        // 如果没有，那么说明是 新建

        form.submit({
            clientValidation : true,
            url : "s/gps/upld",
            waitMsg : "正在保存，请稍后...",
            success : function(form, action) {
                // console.log('=========== success action ', action);
                Ext.Msg.alert('成功', action.result.message);

                view.close();
                view.gridStore.load();

            },
            failure : function(form, action) {
                // console.log('===========action ', action);
                Ext.Msg.alert('失败', action.result.message);
            }
        });
    },

    initializeDragVehicle : function(v) {
        v.dragZone = Ext.create('Ext.dd.DragZone', v.getEl(), {
            getDragData : function(e) {
                var sourceEl = e.getTarget(v.itemSelector, 10), d;
                if (sourceEl) {
                    d = sourceEl.cloneNode(true);
                    d.id = Ext.id();
                    return (v.dragData = {
                        sourceEl : sourceEl,
                        repairXY : Ext.fly(sourceEl).getXY(),
                        ddel : d,
                        vehicle : true,
                        vehicleData : v.getRecord(sourceEl).data
                    });
                }
            },
            getRepairXY : function() {
                return this.dragData.repairXY;
            }
        });
    },

    initializeDropVehicle : function(v) {
        var fset = v;
        fset.dropZone = Ext.create('Ext.dd.DropZone', v.el, {
            getTargetFromEvent : function(e) {
                // return e.getTarget(myGridPanel.getView().rowSelector);
                return true;
            },

            onNodeEnter : function(target, dd, e, data) {
                if (data.vehicle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOut : function(target, dd, e, data) {
                if (data.vehicle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeOver : function(target, dd, e, data) {
                if (data.vehicle) {
                    return Ext.dd.DropZone.prototype.dropAllowed;
                } else {
                    return Ext.dd.DropZone.prototype.dropNotAllowed;
                }
            },

            onNodeDrop : function(target, dd, e, data) {
                if (data.vehicle) {
                    fset.getForm().setValues({
                        'id' : data.vehicleData.id,
                        'vid' : data.vehicleData.vid,
                        'vCode' : data.vehicleData.vCode,
                        'vHeight' : data.vehicleData.vHeight,
                        'vLength' : data.vehicleData.vLength,
                        'vName' : data.vehicleData.vName,
                        'vWidth' : data.vehicleData.vWidth
                    });
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

});

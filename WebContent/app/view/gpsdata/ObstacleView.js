/**
 * 障碍物管理
 */
Ext.define('ivp.view.gpsdata.ObstacleView', {
    extend : 'ivp.desktop.core.Module',
    alias : 'widget.obstacleview',
    itemId : 'obstacleviewId',

    init : function() {
        this.launcher = {
            text : '障碍物管理',
            iconCls : 'icon-grid'
        };
    },
    createWindow : function() {
        var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('obstacleviewId');
        if (!win) {

            var obstacleStore = Ext.create('Ext.data.Store', {
                fields : ['id', 'oName', 'x', 'y','notes'],
                autoLoad : true,
                proxy : {
                    type : 'ajax',
                    url : 's/obtl/s',
                    reader : {
                        type : "json",
                        root : "data",
                        successProperty : 'success',
                        totalProperty : 'total'
                    }
                }
            });

            win = desktop.createWindow({
                id : 'obstacleviewId',
                title : '障碍物管理',
                iconCls : 'icon-grid',
                animCollapse : true,
                constrainHeader : true,
                layout : 'border',

                height : 500,
                width : 900,
                items : [{
                    xtype : 'gridpanel',
                    region : 'center',
                    stripeRows : true,
                    columnLines : true,
                    store : obstacleStore,
                    split : true,
                    columns : [{
                        xtype : 'gridcolumn',
                        dataIndex : 'oName',
                        width : 200,
                        text : '障碍物名称'

                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'x',
                        text : '经度'

                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'y',
                        text : '纬度'
                    }, {
                        xtype : 'gridcolumn',
                        dataIndex : 'notes',
                        width : 200,
                        text : '说明'

                    }],
                    dockedItems : [{
                        xtype : 'pagingtoolbar',
                        store : obstacleStore,
                        dock : 'bottom',
                        displayInfo : true
                    }, {
                        xtype : 'toolbar',
                        dock : 'top',
                        items : [{
                            text : '添加障碍物',
                            handler : me.addObstacle
                        }, '-', {
                            text : '删除',
                            handler : me.deleteObstacle
                        }]
                    }],
                    listeners : {
                        itemdblclick : {
                            fn : me.onUpdateObstacle,
                            scope : me
                        }
                    }

                }]
            });
        }
        return win;
    },

    addObstacle : function(btn) {

        var grid = btn.up('grid');
        var gridStore = grid.getStore();
        var createWin = Ext.create('ivp.view.gpsdata.ObstacleForm', {
            gridStore : gridStore,
            title : '新建障碍物',
            udt : false
        });
        createWin.show();

    },
    
    deleteObstacle : function(btn) {

        var grid = btn.up('grid');
        var record = grid.getSelectionModel().getSelection()[0];
        if (record) {
        } else {
            Ext.MessageBox.alert('', '您好像没有选择任何信息');
            return false
        }

        // console.log('==================record ', record);
        Ext.Msg.confirm("信息", "确定要删除吗？", function(button) {
            if (button == "no") {
                return false;
            }
            var gridStore = grid.getStore();

            var vtID = record.data.id;

            Ext.Ajax.request({
                clientValidation : true,
                url : 's/obtl/dlt',
                params : {
                    "id" : vtID
                },
                success : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('成功', resp.message);
                    gridStore.load();

                },
                failure : function(response, options) {
                    var resp = Ext.decode(response.responseText);
                    Ext.Msg.alert('失败', resp.message);
                }

            });
        });
    },

    onUpdateObstacle : function(grid, record, index, eOpts) {

        var gridStore = grid.getStore();
        var updateWin = Ext.create('ivp.view.gpsdata.ObstacleForm', {
            gridStore : gridStore,
            title : '修改障碍物',
            udt : true
        });
        updateWin.down('form').getForm().setValues(record.data);
        updateWin.show();

    }

});

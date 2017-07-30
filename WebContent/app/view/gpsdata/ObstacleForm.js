/**
 * 修改 障碍物 弹窗
 */
Ext.define('ivp.view.gpsdata.ObstacleForm', {
    extend : 'Ext.window.Window',
    alias : 'widget.obstacleform',
    itemId : 'obstacleformId',

    // title : '修改车队',
    modal : true,
    constrainHeader : true,

    // autoShow : true,
    height : 300,
    width : 500,
    layout : 'fit',

    initComponent : function() {
        var me = this;

        Ext.applyIf(me, {
            items : [{
                xtype : 'form',
                bodyPadding : 10,
                items : [{
                    xtype : 'textfield',
                    // allowBlank : false,
                    hidden : true,
                    name : 'id'
                }, {
                    xtype : 'textfield',
                    anchor : '90%',
                    allowBlank : false,
                    fieldLabel : '障碍物名称',
                    name : 'oName'
                }, {
                    xtype : 'numberfield',
                    anchor : '90%',
                    allowBlank : false,
                    decimalPrecision : 8,
                    fieldLabel : '经度',
                    name : 'x'
                }, {
                    xtype : 'numberfield',
                    anchor : '90%',
                    allowBlank : false,
                    decimalPrecision : 8,
                    fieldLabel : '纬度',
                    name : 'y'
                }, {
                    xtype : 'textareafield',
                    anchor : '90%',
                    rows : 4,
                    // allowBlank : false,
                    fieldLabel : '说明',
                    // emptyText : '',
                    name : 'notes'

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
            }]
        });

        me.callParent(arguments);
    },

    // 关闭按钮
    onCancelButtonClick : function(btn) {
        btn.up('window').close();
    },

    /**
     * 提交
     */
    onSubmit : function(btn, e, eOpts) {
        // 这个是记住 loginview 窗口
        var view = this;
        // 先找到 form这个控件, 从btn 控件开始向上查找
        var formCmp = btn.up('form');
        var form = formCmp.getForm();
        if (form.isValid()) {

        } else {
            Ext.MessageBox.alert('', '请检查必填项');
            return false;
        }

        var values = form.getValues();
        // 如果有id 说明是修改
        // 如果没有，那么说明是 新建
        var submitURL = 's/obtl/udt';
        if (values.id) {
            // submitURL = 's/r/udt';
        } else {
            submitURL = 's/obtl/add'
        }

        form.submit({
            clientValidation : true,
            url : submitURL,
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
    }

});

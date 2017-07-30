/**
 * 修改 车队 弹窗
 */
Ext.define('ivp.view.VehicleForm', {
	extend : 'Ext.window.Window',
	alias : 'widget.userview',
	itemId : 'userviewId',
	requires : ['ztbgl.model.system.SystemRoleModel'],
	
	title : '修改用户',
	modal : true,
	constrainHeader : true,

	autoShow : true,
	height : 400,
	width : 500,
	layout : 'fit',

	initComponent : function() {
		var me = this;
		
		 var roleStore = Ext.create('Ext.data.Store', {
	            model : 'ztbgl.model.system.SystemRoleModel',
	            autoLoad : true,
	            proxy : {
	                type : 'ajax',
	                url : 's/r/s',
	                reader : {
	                    type : "json",
	                    root : "data",
	                    successProperty : 'success',
	                    totalProperty : 'total'
	                }
	            }
	        });
		
		Ext.applyIf(me, {
			items : [ {
				xtype : 'form',
				bodyPadding : 10,
				items : [ {
					xtype : 'textfield',
					// allowBlank : false,
					hidden : true,
					name : 'id'

				}, {
					xtype : 'textfield',
					allowBlank : false,
					fieldLabel : '用户名',
					name : 'userName',
					vtype : 'alphanum',

				}, {
					xtype : 'textfield',
					allowBlank : false,
					fieldLabel : '用户姓名',
					name : 'uName'
				},{
					xtype : 'combobox',
					allowBlank : false,
					fieldLabel : '用户类型',
					name : 'mainRoleID',
					displayField : 'rName',
					valueField : 'id',
					store : roleStore
				}, {
					xtype : 'textfield',
					allowBlank : false,
					fieldLabel : '手机号码',
					name : 'mobileNum',
					enforceMaxLength : true,
					minLength : 11,
                    maxLength : 11,
                    regex : /^\d{0,}$/,
                    regexText : "只能输入11位数字",
				},{
					xtype : 'textfield',
					allowBlank : false,
					fieldLabel : '邮箱',
					name : 'email',
					vtype : 'email',
					regexText : '请输入正确的邮箱地址',
				},{
					xtype : 'textfield',
					allowBlank : false,
					fieldLabel : '身份证号',
					vtype : 'alphanum',
					name : 'idCardNo'
				},{
					xtype : 'combobox',
					allowBlank : false,
					fieldLabel : '用户状态',
					name : 'status',
					displayField : 'n',
					valueField : 'v',
					store : Ext.create('Ext.data.Store', {
						fields : [ 'n', 'v' ],
						data : [ {
							"v" : 'ACTIVE',
							"n" : '正常'
						},{
							"v" : 'PAUSE',
							"n" : '暂停'
						}, {
							"v" : 'HALT',
							"n" : '停用'
						}, {
							"v" : 'DELETE',
							"n" : '删除'
						}]
					})
				},{
					xtype : 'textfield',
					
					fieldLabel : '用户描述',
					name : 'description'

				} ],
				buttons : [ {
					xtype : 'button',
					text : '提交',
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

				} ]
			} ]
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
		var values = form.getValues();
		// 如果有id 说明是修改
		// 如果没有，那么说明是 新建角色
		var submitURL = 's/u/udt';
		if (values.id) {
			// submitURL = 's/r/udt';
		} else {
			submitURL = 's/u/add'
		}

		form.submit({
			clientValidation : true,
			url : submitURL,
			waitMsg : "正在提交，请稍后...",
			success : function(form, action) {
				console.log('=========== success action ',action);
				Ext.Msg.alert('成功', action.result.message);

				view.close();
				view.gridStore.load();

			},
			failure : function(form, action) {
				console.log('===========action ',action);
				Ext.Msg.alert('失败', action.result.message);
			}
		});
	}
});
